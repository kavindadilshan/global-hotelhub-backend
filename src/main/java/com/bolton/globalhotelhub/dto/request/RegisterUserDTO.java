package com.bolton.globalhotelhub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {

    private String fullName;
    private String userName;
    private String mobileNumber;
    private String email;
    private String password;

}
