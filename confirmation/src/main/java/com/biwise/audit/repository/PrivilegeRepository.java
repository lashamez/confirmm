package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PrivilegeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByName(String name);
}
