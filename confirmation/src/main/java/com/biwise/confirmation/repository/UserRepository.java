package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String userName);
    Optional<UserEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}
