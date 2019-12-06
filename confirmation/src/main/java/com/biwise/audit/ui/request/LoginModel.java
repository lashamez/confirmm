package com.biwise.audit.ui.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginModel {

    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

}
