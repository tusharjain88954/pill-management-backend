package com.groww.app.ws.service;

import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.MedicineDto;

import java.util.List;

public interface MedicineService {

    MedicineDto addMedicine(MedicineDto medicineDetails);
    List<MedicineDto> getMedicineDetails(String userId);
    MedicineDto getMedicineDetailsByUserIdAndMedicineId(String UserId, long medicineId);
    MedicineDto updateMedicineDetails(String userId, long medicineId, MedicineDto medicineDetails);
    void deleteMedicine(String userId, long medicineId);
}
