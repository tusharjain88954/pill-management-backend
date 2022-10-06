package com.groww.app.ws.service;

import java.util.List;

import com.groww.app.ws.shared.UserType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.groww.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user, UserType type);
	UserDto getUser(String email, UserType type);
	UserDto getUserByUserId(String userId, UserType type);
	UserDto updateUser(String userId, UserDto user, UserType type);
	void deleteUser(String userId, UserType type);
	List<UserDto> getUsers(int page, int limit, UserType type);
}
