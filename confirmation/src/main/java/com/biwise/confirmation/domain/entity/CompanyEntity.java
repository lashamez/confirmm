package com.biwise.confirmation.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String companyId;
    private String name;
    private Integer yearFounded;
    private String phoneNumber;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinTable(
            name = "manager_company",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")}
    )
    private UserEntity manager;
    @ManyToMany
    @JoinTable(
            name = "accountant_company",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")}
    )
    private List<UserEntity> accountants = new ArrayList<>();
}