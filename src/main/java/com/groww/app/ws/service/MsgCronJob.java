package com.groww.app.ws.service;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.NotificationEntity;
import com.groww.app.ws.io.repository.MedicineRepository;
import com.groww.app.ws.io.repository.NotificationRepository;
import com.groww.app.ws.shared.*;
import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.NotificationDto;
import com.groww.app.ws.shared.dto.UserDto;
import com.groww.app.ws.ui.model.response.MedicineRest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MsgCronJob {
    @Autowired
    MedicineControllerHelper medicineControllerHelper;

    @Autowired
    UserControllerHelper userControllerHelper;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    MedicineRepository medicineRepository;


    @Scheduled(cron = "0 0 0 * * *",zone = "Indian/Maldives")
//    @Scheduled(cron="*/10 * * * * *")
    public void generateNotification(){

        Page<MedicineEntity> medicineEntityPage = medicineRepository.findAll(PageRequest.of(0, 10000));
        List<MedicineEntity> medicineEntityList = medicineEntityPage.getContent();
        for(MedicineEntity medicineEntity : medicineEntityList){
            ModelMapper modelMapper = new ModelMapper();
            MedicineDto medicineDto = modelMapper.map(medicineEntity, MedicineDto.class);
            log.info(medicineDto.getName());
            log.info(medicineDto.getUserId());
            MedicineRest medicineRest = medicineControllerHelper.createMedicineRest(medicineDto);
            if(!validate(medicineDto.getAvailableCount(),medicineRest.getDosages())){
                    NotificationDto notificationDto = NotificationDto.builder()
                            .medicineName(medicineDto.getName())
                            .availableCount(medicineDto.getAvailableCount())
                            .expiryDate(medicineDto.getExpiryDate())
                            .description("Less than 2 days Medicare left before runs out of this Medicine." +
                                    " Kindly contact the needy and get the medicine for the same")
                            .userId(medicineDto.getUserId())
                            .build();

                    // sms sent to
                    userControllerHelper.sentEmergencyMsgToContacts(medicineDto.getUserId(), " Less than 2 days Medicare left before runs out of this "
                            + medicineDto.getName() + " Kindly contact the needy and get the medicine for the same. Pill Assistant Customer Care Team");


                    // notification sent to user/caretaker/patient
                    NotificationEntity notificationEntity = modelMapper.map(notificationDto, NotificationEntity.class);
                    log.info("NotificationEntity userId : " + notificationEntity.getUserId() );
                    notificationRepository.save(notificationEntity);
            }
        }


    }


    public boolean validate(long availableCounts, DosagesContext dosagesContext){



        double medicinesUsedInADay = 0;
        for(DosageContext dosageContext : dosagesContext.getDosageContextList()){
            medicinesUsedInADay = medicinesUsedInADay + dosageContext.getDosesCount();
        }

        log.info("avacnt: "+ availableCounts + " " + "medicineused "+ medicinesUsedInADay);
        // more than 2 day
        if(availableCounts > medicinesUsedInADay* 2L){
            return true;
        }
        return false;
    }



}
