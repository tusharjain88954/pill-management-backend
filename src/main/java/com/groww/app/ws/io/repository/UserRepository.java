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
	
	// ------------------------------------ NATIVE SQL QUERIES -------------------------------------------
	// this is writing native sql queries.
	// since thousands of users can be there in db, paging and sorting repository is used with page and pageable.
	// count query is used just because we have paging and sorting repo.
	@Query(value="select * from users u where u.email_verification_status = 'true'", 
			countQuery="select count(*) from users u where u.email_verification_status = 'true'",
			nativeQuery = true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress( Pageable pageableRequest );
	
	// taking params by positinal params..
	// ?1 ==> place here value of function argument one.
	@Query(value="select * from users u where u.first_name = ?1",nativeQuery=true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	// taking variable name by named params
	@Query(value="select * from users u where u.last_name = :lastName",nativeQuery=true)
	List<UserEntity> findUserByLastName(@Param("lastName") String lastName);
	
	// use of like phrase
	@Query(value="select * from users u where first_name LIKE %:keyword% or last_name LIKE %:keyword%",nativeQuery=true)
	List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);
	
	// selecting the specific columns.
	@Query(value="select u.first_name, u.last_name from users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%",nativeQuery=true)
	List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);
	
	@Transactional // for rollback kind of things.
	@Modifying // as record in database gets updated, will be used for delete SQL query as well.
	@Query(value="update users u set u.email_verification_status=:emailVerificationStatus where u.user_id=:userId", nativeQuery=true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, 
			@Param("userId") String userId);
	
	// ----------------------------------------- JPQL QUERIES ------------------------------------
	// no need for value and nativeQuery attribute
	// instead of class name we use class name, and for column name we use class field names.
	@Query("select user from UserEntity user where user.userId =:userId")
	UserEntity findUserEntityByUserId(@Param("userId") String userId);
	
	// selecting specific columns
	@Query("select user.firstName, user.lastName from UserEntity user where user.userId =:userId")
	List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);
	
	// update query
	@Modifying
    @Transactional 
    @Query("UPDATE UserEntity u set u.emailVerificationStatus =:emailVerificationStatus where u.userId = :userId")
    void updateUserEntityEmailVerificationStatus(
    		@Param("emailVerificationStatus") boolean emailVerificationStatus,
            @Param("userId") String userId);
	
}
