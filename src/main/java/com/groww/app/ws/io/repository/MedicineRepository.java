package com.groww.app.ws.io.repository;

import com.groww.app.ws.io.entity.MedicineEntity;
import com.groww.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends PagingAndSortingRepository<MedicineEntity, Long> {


    MedicineEntity findById(String medicineId);

    List<MedicineEntity> findByUserId(String userId);

    @Query(value="select * from medicine_details m where m.name = ?1 and m.user_id = ?2",nativeQuery=true)
    MedicineEntity findByNameAndUserId(String name, String userId);
    @Query(value="select * from medicine_details m where m.user_id = ?1 and m.id = ?2",nativeQuery=true)
    MedicineEntity findByUserIdAndMedicineId(String userId, long medicineId);

}
