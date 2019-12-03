package com.biwise.confirmation.service;

import com.biwise.confirmation.domain.dto.InvitationTokenDto;
import com.biwise.confirmation.domain.dto.Token;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.dto.VerificationTokenDto;

public interface TokenService {
    void createVerificationToken(UserDto user, String token);
    VerificationTokenDto getVerificationToken(String VerificationToken);
    void createInvitationToken(String mail, String token);
    InvitationTokenDto getInvitationToken(String invitationToken);
    boolean isExpired(Token token);
}
