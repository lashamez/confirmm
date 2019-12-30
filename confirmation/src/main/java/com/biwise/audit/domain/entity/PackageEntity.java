package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PackageEntity {
    private Long id;

    private String packageId;

    private String email;

    private String packageName;
    @JsonIgnore
    private List<UserEntity> users = new ArrayList<>();
}
