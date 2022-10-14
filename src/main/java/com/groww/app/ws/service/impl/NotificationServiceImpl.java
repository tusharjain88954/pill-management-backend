package com.groww.app.ws.service.impl;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.NotificationEntity;
import com.groww.app.ws.io.repository.NotificationRepository;
import com.groww.app.ws.service.NotificationService;
import com.groww.app.ws.service.UserService;
import com.groww.app.ws.shared.UserType;
import com.groww.app.ws.shared.dto.NotificationDto;
import com.groww.app.ws.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    UserService userService;

    @Autowired
    NotificationRepository notificationRepository;




    @Override
    public List<NotificationDto> getNotification(String userId) {
        UserDto userDto = userService.getUserByUserId(userId, UserType.USER);
        ModelMapper modelMapper = new ModelMapper();

        List<NotificationDto> returnValue = new ArrayList<>();
        List<NotificationEntity> notificationEntities = notificationRepository.findByUserId(userId);
        for(NotificationEntity notificationEntity : notificationEntities) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto = modelMapper.map(notificationEntity, NotificationDto.class);
            returnValue.add(notificationDto);
        }

        // copying the user dto to user entity.


        return returnValue;

    }
}
