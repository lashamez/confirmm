package com.biwise.audit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "projectRoles")
public class ProjectRoleEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String role;

    protected ProjectRoleEntity (){

    }

    public ProjectRoleEntity(String role) {
        this.role = role;
    }
}
