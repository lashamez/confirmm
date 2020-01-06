package com.biwise.audit.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = "currentPlan")
public class UserDto {
    private Long id;

    private String userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @JsonIgnore
    private PackageDto currentPlan;

    private Date registerDate;

    private boolean enabled;

    private String langKey;

    private String activationKey;

}
