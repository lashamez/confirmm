package com.biwise.audit.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "project_role")
@Getter
@Setter
public class ProjectRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    protected ProjectRoleEntity (){

    }

    public ProjectRoleEntity(String role) {
        this.role = role;
    }
}
