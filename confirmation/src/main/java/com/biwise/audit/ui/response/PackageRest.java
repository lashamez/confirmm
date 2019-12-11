package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

public class PackageRest {
    private String packageId;
    private String email;
    private String packageName;
    @JsonIgnore
    private List<UserRest> users ;
}
