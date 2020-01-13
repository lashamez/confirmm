package com.biwise.audit.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class InvitationTokenDto implements Token {
    private static final int EXPIRATION = 60 * 24;

    private Long id;

    private String token;

    private String accountantMail;

    private Date expiryDate;

}
