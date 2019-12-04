package com.biwise.confirmation.event.listener;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.event.OnRegistrationCompleteEvent;
import com.biwise.confirmation.service.TokenService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.response.UserRest;
import com.biwise.confirmation.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component(value = "regListener")
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private static final int TOKEN_LENGTH = 50;
    private static final String SERVER_URL = "http://localhost:8080";

    private final Utils utils;

    private final JavaMailSender mailSender;

    private final TokenService tokenService;

    public RegistrationListener(Utils utils, JavaMailSender mailSender, TokenService tokenService) {
        this.utils = utils;
        this.mailSender = mailSender;
        this.tokenService = tokenService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        UserDto user = event.getUser();
        String token = utils.generateConfirmationToken(TOKEN_LENGTH);
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String message = String.format("Active account by clicking below \n " +
                "%s/users/confirm?token=%s", SERVER_URL, token);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
