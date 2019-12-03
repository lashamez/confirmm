package com.biwise.confirmation.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import netscape.security.Privilege;

import javax.persistence.*;
import java.util.Collection;
@Data
@Entity
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {
    public RoleEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;
}