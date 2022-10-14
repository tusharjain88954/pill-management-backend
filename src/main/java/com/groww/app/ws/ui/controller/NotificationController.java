package com.groww.app.ws.ui.controller;

import com.groww.app.ws.io.entity.NotificationEntity;
import com.groww.app.ws.service.NotificationService;
import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.NotificationDto;
import com.groww.app.ws.ui.model.response.MedicineRest;
import com.groww.app.ws.ui.model.response.NotificationRest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping(
            path = "users/{userId}/notification",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<NotificationRest> getNotification(@PathVariable String userId) {
        List<NotificationRest> returnValue = new ArrayList<>();

        List<NotificationDto> notifications = notificationService.getNotification(userId);

        ModelMapper modelMapper = new ModelMapper();

        for(NotificationDto notificationDto : notifications) {
            NotificationRest notificationRest = new NotificationRest();
            notificationRest = modelMapper.map(notificationDto, NotificationRest.class);
            returnValue.add(notificationRest);
        }

        return returnValue;
    }



}
