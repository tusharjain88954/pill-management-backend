package com.groww.app.ws.service;

import com.groww.app.ws.shared.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getNotification(String userId);


}
