package com.biwise.audit.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PackageDto {
    private Long id;

    private String packageId;

    private String email;

    private String packageName;

    @JsonIgnore
    private Set<UserDto> users;
}
