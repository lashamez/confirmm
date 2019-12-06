package com.biwise.audit.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utils {
    private final SecureRandom RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int ITERATIONS = 10000;

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateAddressId(int length) {
        return generateRandomString(length);
    }

    public String generateProjectId(int length) {
        return generateRandomString(length);
    }
    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return returnValue.toString();
    }

    public String generateInvitationToken(int length) {
        return generateRandomString(length);
    }

    public String generateConfirmationToken(int length) {
        return generateRandomString(length);
    }
}