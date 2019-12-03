package com.biwise.confirmation.event.listener;

import com.biwise.confirmation.event.OnInvitationCompleteEvent;
import com.biwise.confirmation.service.TokenService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class InvitationListener implements ApplicationListener<OnInvitationCompleteEvent> {
    private static final int TOKEN_LENGTH = 50;
    private final TokenService tokenService;
    private final JavaMailSender mailSender;
    private final Utils utils;

    public InvitationListener(TokenService tokenService, JavaMailSender mailSender, Utils utils) {
        this.tokenService = tokenService;
        this.mailSender = mailSender;
        this.utils = utils;
    }

    @Override
    public void onApplicationEvent(OnInvitationCompleteEvent invitation) {
        String recipientAddress = invitation.getReceiverMail();
        String subject = "Accountant Invitation";
        String token = utils.generateInvitationToken(TOKEN_LENGTH);
        tokenService.createInvitationToken(recipientAddress, token);
//        String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String message = String.format("%s's manager %s invited you to register as accountant. " +
                        "Proceed to account creation by clicking below\n" +
                        "http://localhost:8080/users/invitation=%s",
                invitation.getCompany().getName(), invitation.getCompany().getManager().getEmail(), token);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
