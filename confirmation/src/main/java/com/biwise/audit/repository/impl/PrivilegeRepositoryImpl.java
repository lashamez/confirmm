package com.biwise.audit.repository.impl;

import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.repository.PrivilegeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PrivilegeRepositoryImpl implements PrivilegeRepository {
    @Override
    public Optional<PrivilegeEntity> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PrivilegeEntity> findAll() {
        return null;
    }

    @Override
    public PrivilegeEntity save(PrivilegeEntity privilegeEntity) {
        return null;
    }

    @Override
    public PrivilegeEntity update(PrivilegeEntity privilegeEntity) {
        return null;
    }

    @Override
    public void delete(PrivilegeEntity privilegeEntity) {

    }

    @Override
    public PrivilegeEntity findByName(String name) {
        return null;
    }
}
