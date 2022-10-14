package com.groww.app.ws.ui.controller;

import com.groww.app.ws.service.MedicineService;
import com.groww.app.ws.service.UserService;
import com.groww.app.ws.shared.MedicineControllerHelper;
import com.groww.app.ws.shared.UserType;
import com.groww.app.ws.shared.dto.MedicineDto;
import com.groww.app.ws.shared.dto.UserDto;
import com.groww.app.ws.ui.model.request.MedicineRequest;
import com.groww.app.ws.ui.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @Autowired
    UserService userService;

    @Autowired
    MedicineControllerHelper medicineControllerHelper;


    @GetMapping(
            path = "users/{userId}/medicine/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public MedicineRest getMedicineDetails(@PathVariable String userId, @PathVariable long id) {
        MedicineRest returnValue = new MedicineRest();

        MedicineDto medicineDto = medicineService.getMedicineDetailsByUserIdAndMedicineId(userId, id);

        returnValue = medicineControllerHelper.createMedicineRest(medicineDto);
        // change byte[] to dosageContext

        return returnValue;
    }

    @PostMapping(
            path = "users/{userId}/medicine",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public MedicineRest addMedicine(@PathVariable String userId, @RequestBody MedicineRequest medicineRequest) throws Exception {

        // client sends data in json/xml format but springboot automatically convert
        // that into java object (userDetails)
        UserDto userDto = userService.getUserByUserId(userId, UserType.USER );
        MedicineRest returnValue = new MedicineRest();

        if (medicineRequest.getName().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        MedicineDto medicineDto = medicineControllerHelper.createMedicineDto(medicineRequest,userId);
        log.info(String.valueOf(medicineDto));
        MedicineDto addedMedicine = medicineService.addMedicine(medicineDto);

        returnValue = medicineControllerHelper.createMedicineRest(addedMedicine);
        return returnValue;
    }

    @PutMapping(
            path = "users/{userId}/medicine/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    // path variable reads data from path.
    public MedicineRest updateUser(@PathVariable String userId, @PathVariable long id, @RequestBody MedicineRequest medicineRequest) {
        MedicineRest returnValue = new MedicineRest();

        MedicineDto medicineDto = new MedicineDto();
        medicineDto = medicineControllerHelper.createMedicineDto(medicineRequest,userId);

        MedicineDto updatedMedicineDetails = medicineService.updateMedicineDetails(userId, id, medicineDto);
        returnValue = medicineControllerHelper.createMedicineRest(updatedMedicineDetails);

        return returnValue;
    }

    @DeleteMapping(
            path = "users/{userId}/medicine/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteUser(@PathVariable String userId, @PathVariable long id) {

        OperationStatusModel returnValue = new OperationStatusModel();

        returnValue.setOperationName(RequestOperationName.DELETE.name());

        medicineService.deleteMedicine(userId, id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());


        return returnValue;
    }

    // this is used for getting users in paginated format.
    @GetMapping(
            path = "users/{userId}/medicine",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MedicineRest> getUsers(
            // these are request params that are used as query strings.
            @PathVariable String userId
    ) {

        List<MedicineRest> returnValue = new ArrayList<>();

        // user service always returns user dto.
        List<MedicineDto> medicines = medicineService.getMedicineDetails(userId);

        // converting users to returnValue.
        // users ---------> returnValue.
        for (MedicineDto medicineDto : medicines) {
            MedicineRest medicineRest = new MedicineRest();
            medicineRest = medicineControllerHelper.createMedicineRest(medicineDto);
            returnValue.add(medicineRest);
        }

        return returnValue;
    }



}
