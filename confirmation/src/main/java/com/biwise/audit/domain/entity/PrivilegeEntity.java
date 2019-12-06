package com.biwise.audit.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Data
@Entity
@NoArgsConstructor
@Table(name = "privilege")
public class PrivilegeEntity {
    public PrivilegeEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles = new ArrayList<>();

}