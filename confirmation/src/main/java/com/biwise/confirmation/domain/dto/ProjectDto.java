package com.biwise.confirmation.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class ProjectDto {
    private Long id;
    private String projectId;
    private String projectName;
    private Date startDate;
    private List<String> users = new ArrayList<>();
}
