package com.groww.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.groww.app.ws.service.PatientCaretakerService;
import com.groww.app.ws.service.TwilioOTPService;
import com.groww.app.ws.shared.*;
import com.groww.app.ws.shared.dto.PatientCaretakerDto;
import com.groww.app.ws.ui.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

import static com.groww.app.ws.shared.UserType.USER;

// rest controller makes it as controller file.
@RestController
@Slf4j
@RequestMapping("users") // http://localhost:8888/users
// all the endpoint in this controller will have cors enabled.
//@CrossOrigin(origins={"http://localhost:8084", "http://localhost:8085"}) //-----------> only for particular origins.
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Helper helper;

	@Autowired
	UserControllerHelper userControllerHelper;

	@Autowired
	PatientCaretakerService patientCaretakerService;




	// produces is used for what type of value we need as response.
	// consumes is used for type of value we give in headers actually.
	@GetMapping(
			path = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest getUser(@PathVariable String id, @RequestParam(value = "type",defaultValue = "user") String type) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id, helper.convertToUserType(type));
//		BeanUtils.copyProperties(userDto, returnValue); // BeanUtils.copyProperties is used for copying the properties.
		returnValue = userControllerHelper.createUserRest(userDto);
		return returnValue;
	}

	@GetMapping(
			path = "/email/{emailId}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest getUserByEmail(@PathVariable String emailId, @RequestParam(value = "type",defaultValue = "user") String type) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByEmail(emailId, helper.convertToUserType(type));
//		BeanUtils.copyProperties(userDto, returnValue); // BeanUtils.copyProperties is used for copying the properties.
		returnValue = userControllerHelper.createUserRest(userDto);
		return returnValue;
	}


	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails, @RequestParam(value = "type",defaultValue = "user") String type) throws Exception {

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
//		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto userDto = userControllerHelper.createUserDto(userDetails);
		log.info( type);
		UserDto createdUser = userService.createUser(userDto,helper.convertToUserType(type));
		// BeanUtils.copyProperties(createdUser, returnValue); // copy createdUser to returnValue
//		returnValue = modelMapper.map(createdUser, UserRest.class);

		returnValue = userControllerHelper.createUserRest(createdUser);

		return returnValue;
	}

	@PutMapping(
			path = "/{id}",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	// path variable reads data from path.
	public UserRest updateUser(@PathVariable String id,
							   @RequestBody UserDetailsRequestModel userDetails,
							   @RequestParam(value = "type",defaultValue = "user") String type) {
		if(userDetails.getEmail() != null){
			throw new UserServiceException(ErrorMessages.EMAIL_ADDRESS_CAN_NOT_BE_CHANGED.getErrorMessage());
		}


		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		userDto = userControllerHelper.createUserDto(userDetails);

		UserDto updateUser = userService.updateUser(id, userDto, helper.convertToUserType(type));
//		BeanUtils.copyProperties(updateUser, returnValue);
		returnValue = userControllerHelper.createUserRest(updateUser);

		return returnValue;
	}

	@DeleteMapping(
			path = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public OperationStatusModel deleteUser(@PathVariable String id,@RequestParam(value = "remarks",defaultValue = "Remarks not found") String remarks , @RequestParam(value = "type",defaultValue = "user") String type) {
		OperationStatusModel returnValue = new OperationStatusModel();

		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id,helper.convertToUserType(type),remarks);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());


		return returnValue;
	}

	// this is used for getting users in paginated format.
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<UserRest> getUsers(
			// these are request params that are used as query strings.
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit,
			@RequestParam(value = "type",defaultValue = "user") String type
	) {

		List<UserRest> returnValue = new ArrayList<>();

		// user service always returns user dto.
		List<UserDto> users = userService.getUsers(page, limit, helper.convertToUserType(type));

		// converting users to returnValue.    
		// users ---------> returnValue.
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
//			BeanUtils.copyProperties(userDto, userModel);
			userModel = userControllerHelper.createUserRest(userDto);
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@PostMapping(
			path = "/{userId}/caretaker/{caretakerId}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public PatientCaretakerRest createPatientCaretakerLink(@PathVariable String userId, @PathVariable String caretakerId) throws Exception {

		PatientCaretakerRest returnValue = new PatientCaretakerRest();

		ModelMapper modelMapper = new ModelMapper();

		PatientCaretakerDto createdPatientCaretakerLink = patientCaretakerService.createPatientCaretakerLink(userId,caretakerId);

		returnValue = modelMapper.map(createdPatientCaretakerLink, PatientCaretakerRest.class);

		return returnValue;
	}

	@PostMapping(
			path = "/{userId}/msg",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public ErrorMessage.EmergencyMsgResponseDto sentEmergencyMsgToContacts(@PathVariable String userId) {
		userControllerHelper.sentEmergencyMsgToContacts(userId, " is in danger. Please Contact him immediately. Pill Assistant Customer Care Team");
		return ErrorMessage.EmergencyMsgResponseDto.builder()
				.status(MsgStatus.DELIVERED)
				.build();
	}







}
	
