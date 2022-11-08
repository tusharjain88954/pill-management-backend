package com.groww.app.ws.ui.model.response;

import com.groww.app.ws.shared.EmergencyContacts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private EmergencyContacts emergencyContacts;

	private String remarks;

	public EmergencyContacts getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(EmergencyContacts emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	// getters and setters
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
