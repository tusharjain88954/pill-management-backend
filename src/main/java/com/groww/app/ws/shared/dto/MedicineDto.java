package com.groww.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto implements Serializable {

    private long id;

    private String name;

    private long expiryDate;

    private long availableCount;

    private long manufacturingDate;

    private byte[] dosages;

    private long dateOfStart;

    private long dateOfEnd;

    private String userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public long getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(long availableCount) {
        this.availableCount = availableCount;
    }

    public long getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(long manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public byte[] getDosages() {
        return dosages;
    }

    public void setDosages(byte[] dosages) {
        this.dosages = dosages;
    }

    public long getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(long dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public long getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(long dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
