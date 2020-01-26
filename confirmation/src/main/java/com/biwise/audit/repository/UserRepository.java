package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String userName);

    UserEntity findByUserId(String userId);

    void deleteByUserId(String userId);

    Optional<UserEntity> findByActivationKey(String token);

    Optional<UserEntity> findByAlias(String alias);

    List<UserEntity> findAllByCurrentPlan(PackageEntity packageEntity);
}
