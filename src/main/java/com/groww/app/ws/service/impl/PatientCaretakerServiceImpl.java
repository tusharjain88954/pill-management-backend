package com.groww.app.ws.service.impl;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.entity.PatientCaretakerEntity;
import com.groww.app.ws.io.entity.UserEntity;
import com.groww.app.ws.io.repository.PatientCaretakerRepository;
import com.groww.app.ws.service.PatientCaretakerService;
import com.groww.app.ws.shared.dto.PatientCaretakerDto;
import com.groww.app.ws.shared.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PatientCaretakerServiceImpl implements PatientCaretakerService {

    @Autowired
    PatientCaretakerRepository patientCaretakerRepository;

    @Override
    public PatientCaretakerDto createPatientCaretakerLink(String userId, String caretakerId) {
        if (patientCaretakerRepository.findByUserIdAndCaretakerId(userId,caretakerId) != null )
            throw new RuntimeException("Record already exists");


        ModelMapper modelMapper = new ModelMapper();
        PatientCaretakerEntity patientCaretakerEntity = new PatientCaretakerEntity();
        patientCaretakerEntity.setCaretakerId(caretakerId);
        patientCaretakerEntity.setUserId(userId);

        PatientCaretakerEntity storedPatientCaretakerDetails = patientCaretakerRepository.save(patientCaretakerEntity);

        PatientCaretakerDto returnValue = modelMapper.map(storedPatientCaretakerDetails, PatientCaretakerDto.class);
        return returnValue;
    }

    @Override
    public List<PatientCaretakerDto> getPatients(String caretakerId) {

        List<PatientCaretakerEntity> patientCaretakerEntities = patientCaretakerRepository.findByCaretakerId(caretakerId);



        List<PatientCaretakerDto> returnValue = new ArrayList<>();
        for(PatientCaretakerEntity patientCaretakerEntity : patientCaretakerEntities) {
            PatientCaretakerDto patientCaretakerDto = new PatientCaretakerDto();
            BeanUtils.copyProperties(patientCaretakerEntity, patientCaretakerDto);
            returnValue.add(patientCaretakerDto);
        }

        return returnValue;

    }
}
