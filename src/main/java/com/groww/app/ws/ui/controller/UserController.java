package com.groww.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groww.app.ws.exceptions.UserServiceException;
import com.groww.app.ws.service.UserService;
import com.groww.app.ws.shared.dto.UserDto;
import com.groww.app.ws.ui.model.request.UserDetailsRequestModel;
import com.groww.app.ws.ui.model.response.ErrorMessages;
import com.groww.app.ws.ui.model.response.OperationStatusModel;
import com.groww.app.ws.ui.model.response.RequestOperationName;
import com.groww.app.ws.ui.model.response.RequestOperationStatus;
import com.groww.app.ws.ui.model.response.UserRest;

// rest controller makes it as controller file.
@RestController
@RequestMapping("users") // http://localhost:8888/users
// all the endpoint in this controller will have cors enabled.
//@CrossOrigin(origins={"http://localhost:8084", "http://localhost:8085"}) //-----------> only for particular origins.
public class UserController {

	@Autowired
	UserService userService;


	// produces is used for what type of value we need as response.
	// consumes is used for type of value we give in headers actually.
	@GetMapping(
			path = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue); // BeanUtils.copyProperties is used for copying the properties.

		return returnValue;
	}

	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

		// client sends data in json/xml format but springboot automatically convert
		// that into java object (userDetails)

		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

		//UserDto userDto = new UserDto();

		// this function doesn't work good when we have nested objects type.
		//BeanUtils.copyProperties(userDetails, userDto); // copy userDetails to userDto

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		// BeanUtils.copyProperties(createdUser, returnValue); // copy createdUser to returnValue
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@PutMapping(
			path = "/{id}",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	// path variable reads data from path.
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updateUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(
			path = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	// we have added cors to delete api with all origins to access this endpoint.
	//@CrossOrigin(origins="*")
	//@CrossOrigin(origins="http://localhost:8084") -----------> only for specific origin.
	//@CrossOrigin(origins={"http://localhost:8084", "http://localhost:8085"}) -----------> only for particular origins.
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();

		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());


		return returnValue;
	}

	// this is used for getting users in paginated format.
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<UserRest> getUsers(
			// these are request params that are used as query strings.
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit
	) {

		List<UserRest> returnValue = new ArrayList<>();

		// user service always returns user dto.
		List<UserDto> users = userService.getUsers(page, limit);

		// converting users to returnValue.    
		// users ---------> returnValue.
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;
	}

}
	
