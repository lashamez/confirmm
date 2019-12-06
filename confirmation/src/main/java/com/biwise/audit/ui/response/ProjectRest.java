package com.biwise.audit.ui.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectRest {
    private String projectId;
    private String projectName;
    private List<String> users;
    private Date startDate;
}
