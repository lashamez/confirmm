package com.biwise.confirmation.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString

public class Message {
    private MessageType messageType;
    private String message;

}
