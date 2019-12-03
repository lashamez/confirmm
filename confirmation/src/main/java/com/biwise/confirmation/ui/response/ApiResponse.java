package com.biwise.confirmation.ui.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse<T> {

    private int status;
    private Message message;
    private T result;

    public ApiResponse(int status, Message message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

}