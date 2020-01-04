package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data

public class UserRest {
    private String userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Date registerDate;

    private boolean enabled;

    private String langKey;

    @JsonIgnore
    private PackageRest packageRest;
}
