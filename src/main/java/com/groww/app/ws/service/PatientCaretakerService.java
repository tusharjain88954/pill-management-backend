package com.groww.app.ws.service;

import com.groww.app.ws.shared.dto.PatientCaretakerDto;

public interface PatientCaretakerService {

    PatientCaretakerDto createPatientCaretakerLink(String userId, String caretakerId);

}
