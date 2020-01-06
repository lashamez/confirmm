package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PackageRest {

    private String packageId;

    private String email;

    private String packageName;

    @JsonIgnore
    private Set<UserRest> users;
}
