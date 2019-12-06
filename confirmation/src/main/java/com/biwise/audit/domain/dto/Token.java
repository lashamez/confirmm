package com.biwise.audit.domain.dto;

import java.util.Date;

public interface Token {
    Date getExpiryDate();
    String getToken();
}
