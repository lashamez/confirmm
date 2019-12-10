package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

}
