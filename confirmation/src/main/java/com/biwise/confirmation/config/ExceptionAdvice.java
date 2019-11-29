package com.biwise.confirmation.config;

import com.biwise.confirmation.ui.response.ApiResponse;
import com.biwise.confirmation.ui.response.Message;
import com.biwise.confirmation.ui.response.MessageType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleNotFoundException(RuntimeException ex) {
        return new ApiResponse<Exception>(400, new Message(MessageType.error,"Bad request"),  null);
    }

}