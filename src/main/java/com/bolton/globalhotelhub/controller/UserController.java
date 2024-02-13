package com.bolton.globalhotelhub.controller;

import com.bolton.globalhotelhub.dto.request.RegisterUserDTO;
import com.bolton.globalhotelhub.dto.request.UpdateUserDTO;
import com.bolton.globalhotelhub.dto.response.CommonDataResponseDTO;
import com.bolton.globalhotelhub.dto.response.UserResponseDTO;
import com.bolton.globalhotelhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {

        userService.registerUser(registerUserDTO);
        return new ResponseEntity<>(new CommonDataResponseDTO(true, "User has been registered successfully..!"),
                HttpStatus.OK);

    }

    @PutMapping(value = "edit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

        userService.updateUser(updateUserDTO);
        return new ResponseEntity<>(new CommonDataResponseDTO(true, "User has been updated successfully..!"),
                HttpStatus.OK);

    }

    @GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {

       List<UserResponseDTO> responseDTOS = userService.getAllUsers();
        return new ResponseEntity<>(new CommonDataResponseDTO(true, responseDTOS),
                HttpStatus.OK);

    }

}
