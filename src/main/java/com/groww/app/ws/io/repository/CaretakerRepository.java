package com.groww.app.ws.io.repository;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CaretakerRepository extends PagingAndSortingRepository<CaretakerEntity, Long> {

    CaretakerEntity findByEmail(String email);
    CaretakerEntity findByUserId(String UserId);


}
