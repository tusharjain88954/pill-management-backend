package com.groww.app.ws.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DosageContext implements Serializable {

    private float dosesCount;

    private LocalTime dosageTime;

    public float getDosesCount() {
        return dosesCount;
    }

    public void setDosesCount(float dosesCount) {
        this.dosesCount = dosesCount;
    }

    public LocalTime getDosageTime() {
        return dosageTime;
    }

    public void setDosageTime(LocalTime dosageTime) {
        this.dosageTime = dosageTime;
    }
}
