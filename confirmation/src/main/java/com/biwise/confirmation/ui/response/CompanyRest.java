package com.biwise.confirmation.ui.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.Collection;

@Data
public class CompanyRest {
    private String companyId;
    private String name;
    private Integer yearFounded;
    private String phoneNumber;
    @JsonBackReference
    private UserRest manager;
    @JsonBackReference
    private Collection<UserRest> accountants;
}
