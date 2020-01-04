package com.biwise.audit.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utils {
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int ITERATIONS = 10000;

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

    public String generatePackageId(int length) {
        return generateRandomString(length);
    }
}
