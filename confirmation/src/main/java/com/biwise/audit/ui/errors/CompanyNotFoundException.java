package com.biwise.audit.ui.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CompanyNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public CompanyNotFoundException(String id) {
        super(ErrorConstants.COMPANY_NOT_FOUND_TYPE, "Company doesn't exist", Status.BAD_REQUEST, id);
    }
}
