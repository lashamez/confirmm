package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PackageRest {

    private String packageId;

    private String email;

    private String packageName;

    @JsonIgnore
    private List<UserRest> users;
}
