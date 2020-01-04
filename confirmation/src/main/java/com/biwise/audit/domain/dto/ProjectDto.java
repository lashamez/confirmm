package com.biwise.audit.domain.dto;

import com.biwise.audit.ui.request.AssignedRole;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;

    private String projectId;

    private String name;

    private String projectType;

    private LocalDate startYear;

    private LocalDate endYear;

    private List<AssignedRole> users = new ArrayList<>();
}
