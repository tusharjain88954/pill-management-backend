package com.groww.app.ws.shared;

import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.groww.app.ws.shared.UserType.CARETAKER;
import static com.groww.app.ws.shared.UserType.USER;

@Component
public class Helper {

    public UserType convertToUserType(String type){
        return (Objects.equals(type, "user") ? USER:CARETAKER);
    }


}
