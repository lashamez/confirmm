package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.RoleEntity;

public interface RoleRepository extends Dao<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
