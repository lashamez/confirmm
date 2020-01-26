package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "project")
@ToString(exclude = "userRoles")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String projectId;

    @NotNull
    private String name;

    @NotNull
    private String projectType;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<AssignedProjectRoleEntity> userRoles = new HashSet<>();

    @NotNull
    private LocalDate startYear;

    @NotNull
    private LocalDate endYear;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity creator;
}
