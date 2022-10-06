package com.groww.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.groww.app.ws.io.entity.CaretakerEntity;
import com.groww.app.ws.io.repository.CaretakerRepository;
import com.groww.app.ws.shared.UserType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.groww.app.ws.exceptions.UserServiceException;
import com.groww.app.ws.ui.model.response.ErrorMessages;
import com.groww.app.ws.io.entity.UserEntity;
import com.groww.app.ws.io.repository.UserRepository;
import com.groww.app.ws.service.UserService;
import com.groww.app.ws.shared.Utils;
import com.groww.app.ws.shared.dto.UserDto;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	// Tells the application context to inject an instance of UserRepository here
	@Autowired
	UserRepository userRepository;


	@Autowired
	CaretakerRepository caretakerRepository;

	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user, UserType type) {
		if (userRepository.findByEmail(user.getEmail()) != null || caretakerRepository.findByEmail(user.getEmail()) != null ) throw new RuntimeException("Record already exists");

		if(type == UserType.CARETAKER) {
			return createCareTaker(user);
		}

		ModelMapper modelMapper = new ModelMapper();

		//BeanUtils.copyProperties(user, userEntity);

		// copying the user dto to user entity.
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		String publicUserId = utils.generateUserId(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);

		//UserDto returnValue = new UserDto();
		// BeanUtils.copyProperties(storedUserDetails, returnValue);

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		return returnValue;
	}

	private UserDto createCareTaker(UserDto user) {

		ModelMapper modelMapper = new ModelMapper();

		//BeanUtils.copyProperties(user, userEntity);

		// copying the user dto to user entity.
		CaretakerEntity caretakerEntity = modelMapper.map(user, CaretakerEntity.class);

		String publicCaretakerId = utils.generateUserId(30);
		caretakerEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		caretakerEntity.setUserId(publicCaretakerId);

		CaretakerEntity storedUserDetails = caretakerRepository.save(caretakerEntity);

		//UserDto returnValue = new UserDto();
		// BeanUtils.copyProperties(storedUserDetails, returnValue);

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		return returnValue;


	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		CaretakerEntity caretakerEntity = caretakerRepository.findByEmail(email);
		if (userEntity == null && caretakerEntity == null)
			throw new UsernameNotFoundException(email);
		
		if(userEntity != null)
			return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
		else
			return new User(caretakerEntity.getEmail(), caretakerEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email, UserType type) {
		if(type == UserType.CARETAKER) {
			log.info(type.getType());
			return getCaretaker(email);
		}
		log.info(type.getType());
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
 
		return returnValue;
	}

	private UserDto getCaretaker(String email) {
		CaretakerEntity caretakerEntity = caretakerRepository.findByEmail(email);

		if (caretakerEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(caretakerEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId, UserType type) {
		if(type == UserType.CARETAKER){
			return getCaretakerByUserId(userId);
		}
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		// ----------- JUST TO CHECK WHETHER NATIVE SQL WORKS ? -------------
//		Pageable pageableRequest = PageRequest.of(0, 3);
//		Page<UserEntity> uE = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
//		List<UserEntity> users = uE.getContent();
//		System.out.print(users.get(0).getEmail());

		if (userEntity == null)
			throw new UsernameNotFoundException("User with ID: " + userId + " not found");

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	private UserDto getCaretakerByUserId(String userId) {
		UserDto returnValue = new UserDto();
		CaretakerEntity caretakerEntity = caretakerRepository.findByUserId(userId);;

		if (caretakerEntity == null)
			throw new UsernameNotFoundException("Caretaker with ID: " + userId + " not found");

		BeanUtils.copyProperties(caretakerEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user, UserType type) {
		if(type == UserType.CARETAKER){
			return updateCaretaker(userId,user);
		}


		UserDto returnValue = new UserDto();

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);

		return returnValue;
	}

	private UserDto updateCaretaker(String userId, UserDto user) {
		UserDto returnValue = new UserDto();

		CaretakerEntity caretakerEntity = caretakerRepository.findByUserId(userId);

		if (caretakerEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		caretakerEntity.setFirstName(user.getFirstName());
		caretakerEntity.setLastName(user.getLastName());

		CaretakerEntity updatedCaretakerDetails = caretakerRepository.save(caretakerEntity);
		BeanUtils.copyProperties(updatedCaretakerDetails, returnValue);

		return returnValue;
	}

	@Override
	public void deleteUser(String userId, UserType type) {
		if(type == UserType.CARETAKER){
			deleteCaretaker(userId);
		}
		else {
			UserEntity userEntity = userRepository.findByUserId(userId);

			if (userEntity == null)
				throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

			// method by spring JPA
			userRepository.delete(userEntity);
		}
	}

	private void deleteCaretaker(String userId) {
		CaretakerEntity caretakerEntity = caretakerRepository.findByUserId(userId);

		if (caretakerEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		// method by spring JPA
		caretakerRepository.delete(caretakerEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit, UserType type) {

		if(type == UserType.CARETAKER){
			return getCaretakers(page,limit);
		}

		List<UserDto> returnValue = new ArrayList<>();
		
		if(page > 0) page--;
		
		// this is made as find all will take pageable type.
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		// it will return list of page user entity.
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		
		// converting
		List<UserEntity> users = usersPage.getContent();
		
		for(UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		
		return returnValue;
	}

	private List<UserDto> getCaretakers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();

		if(page > 0) page--;

		// this is made as find all will take pageable type.
		Pageable pageableRequest = PageRequest.of(page, limit);

		// it will return list of page user entity.
		Page<CaretakerEntity> usersPage = caretakerRepository.findAll(pageableRequest);

		// converting
		List<CaretakerEntity> users = usersPage.getContent();

		for(CaretakerEntity caretakerEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(caretakerEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

}
