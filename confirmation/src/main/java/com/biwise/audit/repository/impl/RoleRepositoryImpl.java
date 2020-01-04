package com.biwise.audit.repository.impl;

import com.biwise.audit.domain.entity.RoleEntity;
import com.biwise.audit.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public Optional<RoleEntity> get(Long id) {

        return Optional.empty();
    }

    @Override
    public List<RoleEntity> findAll() {
        return null;
    }

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return null;
    }

    @Override
    public RoleEntity update(RoleEntity roleEntity) {
        return null;
    }

    @Override
    public void delete(RoleEntity roleEntity) {

    }

    @Override
    public RoleEntity findByName(String name) {
        return null;
    }
}
