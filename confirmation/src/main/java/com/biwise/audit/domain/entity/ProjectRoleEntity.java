package com.biwise.audit.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "project_role")
@Getter
@Setter
@ToString
public class ProjectRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String role;

    protected ProjectRoleEntity (){

    }

    public ProjectRoleEntity(String role) {
        this.role = role;
    }
}
