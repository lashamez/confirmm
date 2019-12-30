package com.biwise.audit.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrivilegeEntity {
    public PrivilegeEntity(String name) {
        this.name = name;
    }

    private Long id;

    private String name;

}