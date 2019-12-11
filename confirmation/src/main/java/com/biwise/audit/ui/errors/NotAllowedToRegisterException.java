package com.biwise.audit.ui.errors;

public class NotAllowedToRegisterException extends RuntimeException {
    public NotAllowedToRegisterException() {
        super("You are not allowed to register. ask manager for invitation!");
    }
}
