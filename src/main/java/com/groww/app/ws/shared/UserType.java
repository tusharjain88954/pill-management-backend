package com.groww.app.ws.shared;

public enum UserType {
    USER("user"),
    CARETAKER("caretaker");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
