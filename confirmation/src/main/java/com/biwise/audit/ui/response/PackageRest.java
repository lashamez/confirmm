package com.biwise.audit.ui.response;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class PackageRest {
    String id;
    String email;
    String packageName;
}
