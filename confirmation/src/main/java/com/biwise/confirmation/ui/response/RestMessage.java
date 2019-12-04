package com.biwise.confirmation.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
public class RestMessage<T> {
    private T result;
    private MessageType messageType;
    private String message;

}