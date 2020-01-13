package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserRest {
    private String userId;

    private String alias;

    private String firstName;

    private String lastName;

    private String email;

    private Date registerDate;

    private boolean enabled;

    private String langKey;

    @JsonIgnore
    private PackageRest packageRest;

}
