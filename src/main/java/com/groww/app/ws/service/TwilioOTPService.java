package com.groww.app.ws.service;

import com.groww.app.ws.config.TwilioConfig;
import com.groww.app.ws.shared.MsgStatus;
import com.groww.app.ws.ui.model.response.ErrorMessage;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioOTPService {
    @Autowired
    private TwilioConfig twilioConfig;


    public ErrorMessage.EmergencyMsgResponseDto sendMsgToContacts(String phoneNo, String userName, String msg) {

        ErrorMessage.EmergencyMsgResponseDto emergencyMsgResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(phoneNo);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otpMessage = "Dear " + userName + ", your patient "+ msg;
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            emergencyMsgResponseDto = new ErrorMessage.EmergencyMsgResponseDto(MsgStatus.DELIVERED);
        } catch (Exception ex) {
            emergencyMsgResponseDto = new ErrorMessage.EmergencyMsgResponseDto(MsgStatus.FAILED);
        }
        return emergencyMsgResponseDto;
    }


}
