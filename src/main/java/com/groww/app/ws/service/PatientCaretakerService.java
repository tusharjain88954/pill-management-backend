package com.groww.app.ws.service;

import com.groww.app.ws.shared.dto.PatientCaretakerDto;

import java.util.List;

public interface PatientCaretakerService {

    PatientCaretakerDto createPatientCaretakerLink(String userId, String caretakerId);

    List<PatientCaretakerDto> getPatients(String CaretakerId);

}
