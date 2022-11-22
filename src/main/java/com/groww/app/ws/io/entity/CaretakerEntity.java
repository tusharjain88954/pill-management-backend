package com.groww.app.ws.io.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="caretaker")
public class CaretakerEntity implements Serializable {

    private static final long serialVersionUID = -8142918651264406445L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String userId; // public caretakerId

    @Column(nullable=false, length=50)
    private String firstName;

    @Column(nullable=false, length=50)
    private String lastName;

    @Column(nullable=false, length=120, unique=true)
    private String email;

    @Column(nullable=false)
    private String encryptedPassword;

    private byte[] emergencyContacts;

    private String remarks;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete;

    public byte[] getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(byte[] emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }



    // one user will have many addresses.
    // userDetails is same as in address entity, for mapping everything is to be same.

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
