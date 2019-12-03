package com.biwise.confirmation.domain.dto;

import com.biwise.confirmation.domain.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
public class VerificationTokenDto implements Token{
    private static final int EXPIRATION = 60 * 24;
    private Long id;
    private String token;
    private UserDto user;
    private Date expiryDate;

}
