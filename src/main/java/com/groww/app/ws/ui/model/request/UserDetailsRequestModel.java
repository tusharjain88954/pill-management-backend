package com.groww.app.ws.ui.model.request;

import com.groww.app.ws.shared.EmergencyContacts;

import java.util.List;

public class UserDetailsRequestModel {
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private EmergencyContacts emergencyContacts;

	private String remarks;

	public EmergencyContacts getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(EmergencyContacts emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	// getters ans setters
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
