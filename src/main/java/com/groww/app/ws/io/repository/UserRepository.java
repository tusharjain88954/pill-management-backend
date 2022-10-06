package com.groww.app.ws.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// this was used before, but it's all methods will be there in paging and sorting repository.
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groww.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	
	// ------------------------------------ IN BUILT QUERY EXCETUTION ------------------------------------
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);

}
