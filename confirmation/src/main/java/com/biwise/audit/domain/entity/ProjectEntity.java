package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectId;

    private String name;

    private String projectType;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<AssignedProjectRoleEntity> userRoles = new HashSet<>();

    private LocalDate startYear;

    private LocalDate endYear;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity creator;
}
