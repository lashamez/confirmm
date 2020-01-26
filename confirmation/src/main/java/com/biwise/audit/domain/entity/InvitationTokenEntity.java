package com.biwise.audit.domain.entity;

import com.biwise.audit.domain.dto.Token;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
public class InvitationTokenEntity implements Token {
    private static final int EXPIRATION = 60 * 24;

    private Long id;

    @NotNull
    private String token;

    @NotNull
    private String accountantMail;

    @NotNull
    private Date expiryDate;

    private void calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        expiryDate = new Date(cal.getTime().getTime());
    }
}
