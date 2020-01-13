package com.biwise.audit.ui.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvitedUserRequestModel {
    private String firstName;

    private String lastName;

    private String email;
}
