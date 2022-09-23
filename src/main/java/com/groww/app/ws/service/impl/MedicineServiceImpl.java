package com.groww.app.ws.service.impl;

import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.UserEntity;
import com.groww.app.ws.io.repository.MedicineRepository;
import com.groww.app.ws.io.repository.UserRepository;
import com.groww.app.ws.service.MedicineService;
import com.groww.app.ws.shared.Utils;
import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;


    @Override
    public MedicineDto addMedicine(MedicineDto medicineDetails) {
        if (medicineRepository.findByNameAndUserId(medicineDetails.getName(),medicineDetails.getUserId()) != null ) throw new RuntimeException("Record already exists");

        ModelMapper modelMapper = new ModelMapper();

        // copying the user dto to user entity.
        MedicineEntity medicineEntity = modelMapper.map(medicineDetails, MedicineEntity.class);

        MedicineEntity storedMedicineDetails = medicineRepository.save(medicineEntity);

        //UserDto returnValue = new UserDto();
        // BeanUtils.copyProperties(storedUserDetails, returnValue);

        MedicineDto returnValue = modelMapper.map(storedMedicineDetails, MedicineDto.class);

        return returnValue;
    }

    @Override
    public MedicineDto getMedicineDetailsByUserIdAndMedicineId(String userId, long medicineId) {
        MedicineDto returnValue = new MedicineDto();
        MedicineEntity medicineEntity = medicineRepository.findByUserIdAndMedicineId(userId, medicineId);

        if (medicineEntity == null)
            throw new IllegalArgumentException("User with ID: " + userId +"And Medicine with ID: " + medicineId + " not found");
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(medicineEntity, MedicineDto.class);

        return returnValue;

    }

    @Override
    public MedicineDto updateMedicineDetails(String userId, long medicineId, MedicineDto medicineDetails) {
        MedicineEntity medicineEntity = medicineRepository.findByUserIdAndMedicineId(userId, medicineId);

        if (medicineEntity == null)
            throw new IllegalArgumentException("User with ID: " + userId +"And Medicine with ID: " + medicineId + " not found");

        MedicineEntity updatedMedicineDetails = medicineRepository.save(medicineEntity);
        MedicineDto medicineDto = new MedicineDto();
        ModelMapper modelMapper = new ModelMapper();
        medicineDto = modelMapper.map(updatedMedicineDetails, MedicineDto.class);
        return medicineDto;
    }

    @Override
    public void deleteMedicine(String userId, long medicineId) {
        MedicineEntity medicineEntity = medicineRepository.findByUserIdAndMedicineId(userId, medicineId);

        if (medicineEntity == null)
            throw new IllegalArgumentException("User with ID: " + userId +"And Medicine with ID: " + medicineId + " not found");

        medicineRepository.delete(medicineEntity);
    }

    @Override
    public List<MedicineDto> getMedicineDetails(String userId) {
        List<MedicineDto> returnValue = new ArrayList<>();



        // it will return list of page user entity.
        List<MedicineEntity> medicines = medicineRepository.findByUserId(userId);

        ModelMapper modelMapper = new ModelMapper();
        for(MedicineEntity medicine : medicines) {
            MedicineDto medicineDto = new MedicineDto();
            medicineDto = modelMapper.map(medicine, MedicineDto.class);
            returnValue.add(medicineDto);
        }

        return returnValue;
    }
}
