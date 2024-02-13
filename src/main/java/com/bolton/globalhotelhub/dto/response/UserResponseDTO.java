package com.bolton.globalhotelhub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

private Long id;
private String contactNumber;
private String email;
private String name;
private String registerDated;
private String userRole;

}