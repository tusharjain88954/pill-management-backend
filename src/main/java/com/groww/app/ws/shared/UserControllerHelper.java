package com.groww.app.ws.shared;

import com.groww.app.ws.service.TwilioOTPService;
import com.groww.app.ws.service.UserService;
import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.UserDto;
import com.groww.app.ws.ui.model.request.MedicineRequest;
import com.groww.app.ws.ui.model.request.UserDetailsRequestModel;
import com.groww.app.ws.ui.model.response.MedicineRest;
import com.groww.app.ws.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

import static com.groww.app.ws.shared.UserType.USER;

@Component
@Slf4j
public class UserControllerHelper {

    @Autowired
    TwilioOTPService twilioOTPService;

    @Autowired
    UserService userService;

    public UserDto createUserDto(UserDetailsRequestModel userDetailsRequestModel) {

        log.info(userDetailsRequestModel.getEmergencyContacts().toString());

        return UserDto.builder()
                .firstName(userDetailsRequestModel.getFirstName())
                .lastName(userDetailsRequestModel.getLastName())
                .email(userDetailsRequestModel.getEmail())
                .password(userDetailsRequestModel.getPassword())
                .emergencyContacts(emergencyContactsToByteArrayConverter(userDetailsRequestModel.getEmergencyContacts()))
                .build();
    }

    public UserRest createUserRest(UserDto userDto) {


        return UserRest.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .emergencyContacts(byteArrayToEmergencyContactsConverter(userDto.getEmergencyContacts()))
                .build();
    }

    public byte[] emergencyContactsToByteArrayConverter(EmergencyContacts emergencyContacts){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(emergencyContacts);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EmergencyContacts byteArrayToEmergencyContactsConverter(byte[] emergencyContacts){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(emergencyContacts);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            EmergencyContacts deserializedUser = (EmergencyContacts) ois.readObject();
            return deserializedUser;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void sentEmergencyMsgToContacts(String userId, String msg){
        log.info("come inside sentEmergencyMsgToContacts");
        UserDto userDto = userService.getUserByUserId(userId, USER);
        UserRest userRest = createUserRest(userDto);
        for(Contact contact : userRest.getEmergencyContacts().getContactlist()) {
            twilioOTPService.sendMsgToContacts(contact.getContactNumber(), contact.getName(), userRest.getFirstName() + msg);
        }
        log.info("DELIVERED SUCCESSFULLY");
    }


}
