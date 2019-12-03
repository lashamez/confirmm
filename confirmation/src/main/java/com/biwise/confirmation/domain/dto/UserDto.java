package com.biwise.confirmation.domain.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class UserDto {

    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date registerDate;
    private boolean enabled;
    private Collection<CompanyDto> ownedCompanies;
    private Collection<CompanyDto> accountantCompanies;

}