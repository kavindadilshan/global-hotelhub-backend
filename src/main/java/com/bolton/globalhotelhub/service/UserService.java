package com.bolton.globalhotelhub.service;

import com.bolton.globalhotelhub.dto.request.RegisterUserDTO;
import com.bolton.globalhotelhub.dto.request.UpdateUserDTO;
import com.bolton.globalhotelhub.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    void registerUser(RegisterUserDTO registerUserDTO);

    void updateUser(UpdateUserDTO updateUserDTO);

    List<UserResponseDTO> getAllUsers();

}
