package com.biwise.audit.ui.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class PackageRequestModel {
    @Email(message = "გთხოვთ ჩაწერეთ სწორი ელ-ფოსტა")
    String email;

    String packageName;
}
