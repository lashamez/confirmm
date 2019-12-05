package com.biwise.confirmation.ui.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
public class ProjectRequestModel {
    @NotBlank(message = "Project name cannot be empty")
    private String projectName;
    private List<@Email String> users;
}
