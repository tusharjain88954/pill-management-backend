package com.groww.app.ws.service.impl;

import com.groww.app.ws.io.entity.PatientCaretakerEntity;
import com.groww.app.ws.io.entity.UserEntity;
import com.groww.app.ws.io.repository.PatientCaretakerRepository;
import com.groww.app.ws.service.PatientCaretakerService;
import com.groww.app.ws.shared.dto.PatientCaretakerDto;
import com.groww.app.ws.shared.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
