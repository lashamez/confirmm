package com.biwise.audit.ui.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Future;
import java.util.Date;

@Getter
@ToString
public class ProjectTaskRequestModel {

    private String task;

    @Future(message = "დედლაინი უნდა იყოს მომავალი თარიღი")
    private Date deadLineDate;
}
