package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.domain.entity.RoleEntity;

public interface PrivilegeRepository extends Dao<PrivilegeEntity, Long> {
    PrivilegeEntity findByName(String name);

}
