package com.biwise.audit.domain.entity;

import com.biwise.audit.ui.request.AssignedRole;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String projectId;

    private String name;

    private String projectType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"))
    private List<AssignedRole> users = new ArrayList<>();

    private LocalDate startYear;

    private LocalDate endYear;
}
