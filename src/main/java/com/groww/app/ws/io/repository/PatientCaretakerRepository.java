package com.groww.app.ws.io.repository;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.PatientCaretakerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PatientCaretakerRepository extends PagingAndSortingRepository<PatientCaretakerEntity, Long> {
    List<PatientCaretakerEntity> findByUserId(String UserId);

    List<PatientCaretakerEntity> findByCaretakerId(String caretakerId);

    PatientCaretakerEntity findByUserIdAndCaretakerId(String userId,String caretakerId);
}
