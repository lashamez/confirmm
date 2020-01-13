package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
