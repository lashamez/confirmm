package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "UserRest{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", enabled=" + enabled +
                ", langKey='" + langKey + '\'' +
                ", packageRest=" + packageRest +
                '}';
    }
}
