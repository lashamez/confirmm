package com.biwise.confirmation.ui.errors;

public class CompanyAlreadyUsedException extends BadRequestAlertException {
    public CompanyAlreadyUsedException() {
        super(ErrorConstants.COMPANY_ALREADY_USED_TYPE, "Company is already registered!", "company", "name");
    }
}
