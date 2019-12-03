package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.CompanyEntity;
import com.biwise.confirmation.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {
    void deleteByCompanyId(String companyId);
    Optional<CompanyEntity> findByCompanyId(String companyId);
    Optional<CompanyEntity> findByName(String companyName);
    List<CompanyEntity> findByManagerUserId(String userId);
}
