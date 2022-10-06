package com.groww.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCaretakerDto implements Serializable {

    private static final long serialVersionUID = 8745134639058100779L;

    private long id;

    private String userId; // public userId/patientId

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
