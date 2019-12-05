package com.biwise.confirmation.service;


import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.domain.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.biwise.confirmation.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailService {
    private static final int TOKEN_LENGTH = 50;

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    @Value("${confirmation.mail.from}")
    private String mailFrom;
    private static final String SERVER_URL = "http://localhost:8080";

    private final Utils utils;
    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(Utils utils, JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.utils = utils;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(mailFrom);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(UserDto user, String templateName, String titleKey, String activationUrl) {
        Locale locale = Locale.ENGLISH;
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, mailFrom);
        context.setVariable("activationUrl", activationUrl);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(UserDto user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        String activationUrl = "localhost:8080/users/confirm?token="+user.getActivationKey();
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title",activationUrl);
    }

    @Async
    public void sendCreationEmail(UserDto user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title", null);
    }

    @Async
    public void sendPasswordResetMail(UserDto user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title", null);
    }

    @Async
    public void sendProjectInvitation(ProjectDto project, String email) {
        log.debug("Sending invitation mail");
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        Locale locale = Locale.ENGLISH;
        Context context = new Context(locale);
        context.setVariable(USER, email);
        context.setVariable("project", project);
        context.setVariable("invitationUrl", "localhost:8080/#fixme");
        String content = templateEngine.process("mail/invitationEmail", context);
        String subject = messageSource.getMessage("email.invitation.title", null, locale);
        sendEmail(email, subject, content, false, true);
    }
}
