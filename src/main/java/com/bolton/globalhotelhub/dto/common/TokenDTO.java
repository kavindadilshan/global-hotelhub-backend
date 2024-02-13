package com.bolton.globalhotelhub.dto.common;


import com.bolton.globalhotelhub.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private long id;
    private String fullName;
    private String email;
    private String contactNumber;
    private UserRole userRole;



}
