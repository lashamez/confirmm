package com.biwise.audit.domain.dto;

import lombok.Data;

@Data
public class PackageDto {
    private Long id;
    private String packageId;
    private String email;
    private String packageName;
}
