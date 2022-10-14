package com.groww.app.ws.io.entity;

import javax.persistence.*;

@Entity
@Table(name="medicine_details")
public class MedicineEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false, length=200)
    private String name;

    @Column(nullable=false)
    private long expiryDate;

    private long availableCount;

    private long manufacturingDate;

    @Column(nullable=false)
    private byte[] dosages;

    @Column(nullable=false)
    private long dateOfStart;

    @Column(nullable=false)
    private long dateOfEnd;

    @Column(nullable=false)
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
