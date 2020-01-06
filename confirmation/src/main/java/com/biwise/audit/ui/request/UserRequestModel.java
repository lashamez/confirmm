package com.biwise.audit.ui.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequestModel {
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 50)
    private String username;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name length must be in range 1-50")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name length must be in range 1-50")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 50, message = "Password length must be in range 4-50")
    private String password;

}
