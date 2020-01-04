package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "package")

public class PackageEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String packageId;

    private String email;

    private String packageName;

    @JsonIgnore
    @OneToMany(mappedBy = "currentPlan", fetch = FetchType.EAGER)
    private List<UserEntity> users = new ArrayList<>();
}
