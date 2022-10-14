package com.groww.app.ws.io.repository;

import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.NotificationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends PagingAndSortingRepository<NotificationEntity, Long> {


    List<NotificationEntity> findByUserId(String userId);

}