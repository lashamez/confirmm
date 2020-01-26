package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "project_users_role")
@Getter
@Setter
@ToString
public class AssignedProjectRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    @NotNull
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ProjectRoleEntity role;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonBackReference
    private ProjectEntity project;
}
