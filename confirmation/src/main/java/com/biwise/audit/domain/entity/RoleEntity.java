package com.biwise.audit.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Data
@NoArgsConstructor
public class RoleEntity {
    public RoleEntity(String name) {
        this.name = name;
    }

    private Long id;

    private String name;

    private Collection<PrivilegeEntity> privileges = new ArrayList<>();
}