package com.biwise.confirmation.event;

import com.biwise.confirmation.domain.dto.CompanyDto;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
@Data
public class OnInvitationCompleteEvent extends ApplicationEvent {
    private CompanyDto company;
    private String receiverMail;

    public OnInvitationCompleteEvent(CompanyDto company, String receiverMail) {
        super(receiverMail);
        this.company = company;
        this.receiverMail = receiverMail;
    }
}
