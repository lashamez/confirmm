package com.biwise.audit.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvitationTokenDto implements Token {
    private static final int EXPIRATION = 60 * 24;

    private Long id;

    private String token;

    private String accountantMail;

    private Date expiryDate;

}
