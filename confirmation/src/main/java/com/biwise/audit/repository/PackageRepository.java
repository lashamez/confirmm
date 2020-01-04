package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    Optional<PackageEntity> findByEmail(String email);
}
