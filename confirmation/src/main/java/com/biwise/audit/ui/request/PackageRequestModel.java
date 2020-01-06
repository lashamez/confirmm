package com.biwise.audit.ui.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class PackageRequestModel {
    @Email(message = "გთხოვთ ჩაწერეთ სწორი ელ-ფოსტა")
    String email;

    String packageName;
}
