package com.biwise.audit.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String projectId;
    private String projectName;
    private Date startDate;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"))
    private List<String> users = new ArrayList<>();

    @PrePersist
    void dateCreated() {
        startDate = new Date();
    }

}
