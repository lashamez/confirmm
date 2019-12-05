package com.biwise.confirmation.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String projectId;
    private String projectName;
    private Date startDate;
    @ElementCollection
    private List<String> users = new ArrayList<>();

    @PrePersist
    void dateCreated() {
        startDate = new Date();
    }

}
