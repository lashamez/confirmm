package com.biwise.confirmation.event;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.ui.response.UserRest;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserDto user;

    public OnRegistrationCompleteEvent(UserDto user, Locale locale, String appUrl) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

}
