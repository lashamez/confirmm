package com.biwise.confirmation.ui.response;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class ProjectRest {
    private String projectId;
    private String projectName;
    private List<String> users;
    private Date startDate;
}
