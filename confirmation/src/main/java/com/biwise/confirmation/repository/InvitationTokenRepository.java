package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.InvitationTokenEntity;
import com.biwise.confirmation.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface InvitationTokenRepository extends CrudRepository<InvitationTokenEntity, Long> {
    InvitationTokenEntity findByToken(String token);

    InvitationTokenEntity findByAccountantMail(String accountantMail);
}
