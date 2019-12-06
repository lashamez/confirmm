package com.biwise.audit.ui.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Date registerDate;
    private boolean enabled;
    private String langKey;

}
