package com.biwise.audit.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
