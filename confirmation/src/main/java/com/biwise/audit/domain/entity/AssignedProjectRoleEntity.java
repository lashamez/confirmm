package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "project_users_roles")
@Getter
@Setter
public class AssignedProjectRoleEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private ProjectRoleEntity role;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private ProjectEntity project;
}
