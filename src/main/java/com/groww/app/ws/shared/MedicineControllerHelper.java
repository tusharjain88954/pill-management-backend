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

        return MedicineDto.builder()
                .name(medicineRequest.getName())
                .expiryDate(getEpochMillisFromLocalDate(medicineRequest.getExpiryDate()))
                .userId(userId)
                .manufacturingDate(getEpochMillisFromLocalDate(medicineRequest.getManufacturingDate()))
                .availableCount(medicineRequest.getAvailableCount())
                .dateOfStart(getEpochMillisFromLocalDate(medicineRequest.getDateOfStart()))
                .dateOfEnd(getEpochMillisFromLocalDate(medicineRequest.getDateOfEnd()))
                .dosages(dosageContextToByteArrayConverter(medicineRequest.getDosages()))
                .build();
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
