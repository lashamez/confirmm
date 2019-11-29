package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}
