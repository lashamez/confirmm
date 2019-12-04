package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.InvitationTokenDto;
import com.biwise.confirmation.domain.dto.Token;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.dto.VerificationTokenDto;
import com.biwise.confirmation.domain.entity.InvitationTokenEntity;
import com.biwise.confirmation.domain.entity.VerificationTokenEntity;
import com.biwise.confirmation.repository.InvitationTokenRepository;
import com.biwise.confirmation.repository.VerificationTokenRepository;
import com.biwise.confirmation.service.TokenService;
import com.biwise.confirmation.ui.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TokenServiceImpl implements TokenService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final VerificationTokenRepository verificationTokenRepository;
    private final InvitationTokenRepository invitationTokenRepository;

    public TokenServiceImpl(VerificationTokenRepository verificationTokenRepository, InvitationTokenRepository invitationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.invitationTokenRepository = invitationTokenRepository;
    }

    @Override
    public void createVerificationToken(UserDto user, String token) {
        VerificationTokenDto verificationToken = new VerificationTokenDto();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationTokenRepository.save(modelMapper.map(verificationToken, VerificationTokenEntity.class));
    }

    @Override
    public VerificationTokenDto getVerificationToken(String verificationToken) {
        return modelMapper.map(verificationTokenRepository.findByToken(verificationToken), VerificationTokenDto.class);
    }

    @Override
    public void createInvitationToken(String mail, String token) {
        InvitationTokenDto invitationToken = new InvitationTokenDto();
        invitationToken.setAccountantMail(mail);
        invitationToken.setToken(token);
        invitationTokenRepository.save(modelMapper.map(invitationToken, InvitationTokenEntity.class));
    }

    @Override
    public InvitationTokenDto getInvitationToken(String invitationToken) {
        return modelMapper.map(invitationTokenRepository.findByToken(invitationToken), InvitationTokenDto.class);
    }

    @Override
    public boolean isExpired(Token token) {
        Calendar cal = Calendar.getInstance();
        return token.getExpiryDate().getTime() - cal.getTime().getTime() <= 0;
    }
}
