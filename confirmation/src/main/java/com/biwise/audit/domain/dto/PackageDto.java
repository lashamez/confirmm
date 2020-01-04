package com.biwise.audit.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class PackageDto {
    private Long id;

    private String packageId;

    private String email;

    private String packageName;

    @JsonIgnore
    private List<UserDto> users;
}
