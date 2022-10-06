package com.groww.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(indexes = @Index(name = "uniqueMultiIndex", columnList = "userId, caretakerId", unique = true), name="patient_caretaker")
public class PatientCaretakerEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String userId; // public userId/patientId

    @Column(nullable=false)
    private String caretakerId; //  public userId/caretakerId

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaretakerId() {
        return caretakerId;
    }

    public void setCaretakerId(String caretakerId) {
        this.caretakerId = caretakerId;
    }
}
