package com.biwise.audit.domain.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectEntity {
    private Long id;
    private String projectId;
    private String name;
    private String projectType;
    private List<String> users = new ArrayList<>();
    private LocalDate startYear;
    private LocalDate endYear;
}
