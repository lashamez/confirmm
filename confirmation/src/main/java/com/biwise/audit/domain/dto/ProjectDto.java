package com.biwise.audit.domain.dto;

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

    private List<String> users = new ArrayList<>();
}
