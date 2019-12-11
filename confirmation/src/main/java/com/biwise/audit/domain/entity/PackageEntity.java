package com.biwise.audit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "plan")

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
