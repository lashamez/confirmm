package com.biwise.audit.ui.response;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProjectRest {
    private String projectId;
    private List<String> users = new ArrayList<>();
    private String name;
    private String projectType;
    private String startYear;
    private String endYear;
}
