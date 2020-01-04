package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PackageEntity;

import java.util.Optional;

public interface PackageRepository extends Dao<PackageEntity, Long> {
    Optional<PackageEntity> findByEmail(String email);
}
