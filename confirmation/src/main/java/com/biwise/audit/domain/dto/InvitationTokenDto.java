package com.biwise.audit.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InvitationTokenDto implements Token{
    private static final int EXPIRATION = 60 * 24;
    private Long id;
    private String token;
    private String accountantMail;
    private Date expiryDate;

}
