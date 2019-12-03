package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.domain.entity.VerificationTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity, Long> {
    VerificationTokenEntity findByToken(String token);

    VerificationTokenEntity findByUser(UserEntity user);
}
