package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<UserEntity, Long> {
    List<UserEntity> findAllByCurrentPlan(PackageEntity packageEntity);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByActivationKey(String token);
    Optional<UserEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
    Optional<UserEntity> findByEmail(String email);
}
