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
public class EmergencyContacts implements Serializable {

    List<Contact> contactlist;


    public List<Contact> getContactlist() {
        return contactlist;
    }

    public void setContactlist(List<Contact> contactlist) {
        this.contactlist = contactlist;
    }
}
