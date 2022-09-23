package com.groww.app.ws.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DosagesContext implements Serializable {
    private List<DosageContext> dosageContextList;

    public List<DosageContext> getDosageContextList() {
        return dosageContextList;
    }

    public void setDosageContextList(List<DosageContext> dosageContextList) {
        this.dosageContextList = dosageContextList;
    }
}
