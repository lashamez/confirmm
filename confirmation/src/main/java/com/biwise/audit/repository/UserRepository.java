package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String userName);
    Optional<UserEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
    Optional<UserEntity> findByActivationKey(String token);

    Optional<UserEntity> findByUsername(String username);
}
