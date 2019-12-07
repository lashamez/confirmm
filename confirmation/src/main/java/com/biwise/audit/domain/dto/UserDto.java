package com.biwise.audit.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class UserDto {

    private Long id;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date registerDate;
    private boolean enabled;
    private String langKey;
    private String activationKey;
}