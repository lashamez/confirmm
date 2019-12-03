package com.biwise.confirmation.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDto {
    private Long id;
    private String companyId;
    private String name;
    private Integer yearFounded;
    private String phoneNumber;
    private UserDto manager;
    private List<UserDto> accountants = new ArrayList<>();
}
