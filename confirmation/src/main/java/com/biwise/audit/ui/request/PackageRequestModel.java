package com.biwise.audit.ui.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class PackageRequestModel {
    @Email(message = "გთხოვთ ჩაწერეთ სწორი ელ-ფოსტა")
    String email;
    String packageName;
}
