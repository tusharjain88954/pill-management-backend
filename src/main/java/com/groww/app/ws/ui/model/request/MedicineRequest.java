package com.groww.app.ws.ui.model.request;

import com.groww.app.ws.shared.DosagesContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequest {

    private long id;

    private String name;

    private LocalDate expiryDate;

    private int availableCount;

    private LocalDate manufacturingDate;

    private DosagesContext dosages;

    private LocalDate dateOfStart;

    private LocalDate dateOfEnd;


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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public DosagesContext getDosages() {
        return dosages;
    }

    public void setDosages(DosagesContext dosages) {
        this.dosages = dosages;
    }

    public LocalDate getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(LocalDate dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public LocalDate getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(LocalDate dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }
}
