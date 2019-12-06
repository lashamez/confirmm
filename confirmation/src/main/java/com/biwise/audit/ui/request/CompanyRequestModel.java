package com.biwise.audit.ui.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CompanyRequestModel {
    @NotBlank(message = "Company name is required")
    @Size(min = 1, max = 100,message = "Company name length must be in range 1-100")
    private String name;
    @Range(min = 1900, max = 2019, message = "Year of foundation must be between 1900-2019")
    private Integer yearFounded;
    @Pattern(regexp = "(\\+995|0)*[0-9]{9}", message = "Invalid phone number")
    private String phoneNumber;

    private List<String> accountantMails;
}
