package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.InvitationTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface InvitationTokenRepository extends CrudRepository<InvitationTokenEntity, Long> {
    InvitationTokenEntity findByToken(String token);

    InvitationTokenEntity findByAccountantMail(String accountantMail);
}
