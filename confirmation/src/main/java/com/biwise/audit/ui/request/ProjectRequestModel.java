package com.biwise.audit.ui.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
@Data
public class ProjectRequestModel {
    @NotBlank(message = "Project name cannot be empty")
    private String name;
    @NotBlank(message = "ProjectType cannot be empty")
    private String projectType;
    @NotBlank(message = "Start date can't be empty")
    private String startYear;
    @NotBlank(message = "Finishing date can't be empty")
    private String endYear;
}
