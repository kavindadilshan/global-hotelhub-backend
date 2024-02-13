package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.entity.Users;
import com.bolton.globalhotelhub.dto.request.RegisterUserDTO;
import com.bolton.globalhotelhub.dto.request.UpdateUserDTO;
import com.bolton.globalhotelhub.dto.response.UserResponseDTO;
import com.bolton.globalhotelhub.enums.UserRole;
import com.bolton.globalhotelhub.exception.GlobalHotelHubServiceException;
import com.bolton.globalhotelhub.repository.UserRepository;
import com.bolton.globalhotelhub.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.bolton.globalhotelhub.constant.ErrorCodeConstant.REQUEST_FAIL;
import static com.bolton.globalhotelhub.constant.ErrorCodeConstant.RESOURCE_NOT_FOUND;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        LOGGER.info("Execute Function : registerUser");

        try {

            Users users = userRepository.findByUsername(registerUserDTO.getUserName());

            if (users != null)
                throw new GlobalHotelHubServiceException(REQUEST_FAIL, "Username already exists");

            Users usersObj = new Users();
            usersObj.setName(registerUserDTO.getFullName());
            usersObj.setUsername(registerUserDTO.getUserName());
            usersObj.setUserRole(UserRole.PUBLIC_USER);
            usersObj.setEmail(registerUserDTO.getEmail());
            usersObj.setContactNumber(registerUserDTO.getMobileNumber());
            usersObj.setRegisteredDateTime(new Date());
            usersObj.setPassword(bCryptPasswordEncoder.encode(registerUserDTO.getPassword()));

            userRepository.save(usersObj);


        } catch (Exception e) {
            LOGGER.error("Function : saveItem  : " + e.getMessage());
            throw e;
        }


    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {

        LOGGER.info("Execute Function : updateUser");

        try {

            Optional<Users> user = userRepository.findById(updateUserDTO.getId());

            if (!user.isPresent())
                throw new GlobalHotelHubServiceException(RESOURCE_NOT_FOUND, "User not found");


            Users usersObj = user.get();
            usersObj.setName(updateUserDTO.getFullName());
            usersObj.setEmail(updateUserDTO.getEmail());
            usersObj.setContactNumber(updateUserDTO.getMobileNumber());

            userRepository.save(usersObj);


        } catch (Exception e) {
            LOGGER.error("Function : updateUser  : " + e.getMessage());
            throw e;
        }


    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        LOGGER.info("Execute Function : getAllUsers");

        try {


            List<Users> users = userRepository.findAll();

            List<UserResponseDTO> responseDTOS = new ArrayList<>();

            for (Users usersObj : users) {

                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setId(usersObj.getId());
                userResponseDTO.setName(usersObj.getName());
                userResponseDTO.setEmail(usersObj.getEmail());
                userResponseDTO.setContactNumber(usersObj.getContactNumber());
                userResponseDTO.setRegisterDated(usersObj.getRegisteredDateTime().toString());
                userResponseDTO.setUserRole(usersObj.getUserRole().toString());

                responseDTOS.add(userResponseDTO);

            }

            return responseDTOS;


        } catch (Exception e) {
            LOGGER.error("Function : getAllUsers  : " + e.getMessage());
            throw e;
        }
    }
}
