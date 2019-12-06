package com.biwise.audit.domain.entity;

import com.biwise.audit.domain.dto.Token;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class InvitationTokenEntity implements Token {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private String accountantMail;
    private Date expiryDate;

    @PrePersist
    private void calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        expiryDate = new Date(cal.getTime().getTime());
    }
}
