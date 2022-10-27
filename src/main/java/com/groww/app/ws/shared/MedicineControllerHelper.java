package com.groww.app.ws.shared;

import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.ui.model.request.MedicineRequest;
import com.groww.app.ws.ui.model.response.MedicineRest;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class MedicineControllerHelper {


    public MedicineDto createMedicineDto(MedicineRequest medicineRequest, String userId) {

        MedicineDto medicineDto =  MedicineDto.builder()
                .userId(userId)
                .build();

        if(medicineRequest.getName() != null)
            medicineDto.setName(medicineRequest.getName());

        if(medicineRequest.getExpiryDate() != null)
            medicineDto.setExpiryDate(getEpochMillisFromLocalDate(medicineRequest.getExpiryDate()));

        if(medicineRequest.getDateOfEnd() != null)
            medicineDto.setDateOfEnd(getEpochMillisFromLocalDate(medicineRequest.getDateOfEnd()));

        if(medicineRequest.getDateOfStart() != null)
            medicineDto.setDateOfStart(getEpochMillisFromLocalDate(medicineRequest.getDateOfStart()));

        if(medicineRequest.getManufacturingDate() != null)
            medicineDto.setManufacturingDate(getEpochMillisFromLocalDate(medicineRequest.getManufacturingDate()));

        if(medicineRequest.getAvailableCount() != 0)
            medicineDto.setAvailableCount(medicineRequest.getAvailableCount());

        if(medicineRequest.getDosages() != null)
            medicineDto.setDosages(dosageContextToByteArrayConverter(medicineRequest.getDosages()));

        return medicineDto;
    }


    public long getEpochMillisFromLocalDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    public LocalDate getLocalDateFromEpochMillis(long timeInMillis) {
        return Instant.ofEpochMilli(timeInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public byte[] dosageContextToByteArrayConverter(DosagesContext dosageContext){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(dosageContext);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DosagesContext byteArrayToDosageContextConverter(byte[] dosageContext){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(dosageContext);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            DosagesContext deserializedUser = (DosagesContext) ois.readObject();
            return deserializedUser;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicineRest createMedicineRest(MedicineDto addedMedicine) {

        return MedicineRest.builder()
                .id(addedMedicine.getId())
                .name(addedMedicine.getName())
                .expiryDate(getLocalDateFromEpochMillis(addedMedicine.getExpiryDate()))
                .userId(addedMedicine.getUserId())
                .manufacturingDate(getLocalDateFromEpochMillis(addedMedicine.getManufacturingDate()))
                .availableCount(addedMedicine.getAvailableCount())
                .dateOfStart(getLocalDateFromEpochMillis(addedMedicine.getDateOfStart()))
                .dateOfEnd(getLocalDateFromEpochMillis(addedMedicine.getDateOfEnd()))
                .dosages(byteArrayToDosageContextConverter(addedMedicine.getDosages()))
                .build();
    }
}
