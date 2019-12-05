package com.biwise.confirmation.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestMessage<T> {
    private T result;
    private MessageType messageType;
    private String message;

}