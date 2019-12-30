package com.biwise.audit.repository.impl;

import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.repository.PackageRepository;
import com.biwise.audit.repository.mapper.PackageMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Repository
public class PackageRepositoryImpl implements PackageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PackageMapper packageMapper;

    public PackageRepositoryImpl(JdbcTemplate jdbcTemplate, PackageMapper packageMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.packageMapper = packageMapper;
    }

    @Override
    public Optional<PackageEntity> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from package where email=?", new Object[]{email}, packageMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PackageEntity> get(Long aLong) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from package where id=?", new Object[]{aLong}, packageMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageEntity> findAll() {
        return null;
    }

    @Override
    public PackageEntity save(PackageEntity packageEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into package (package_id, email, package_name) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, packageEntity.getPackageId());
            ps.setString(2, packageEntity.getEmail());
            ps.setString(3, packageEntity.getPackageName());
            return ps;
        }, keyHolder);
        packageEntity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return packageEntity;
    }

    @Override
    public PackageEntity update(PackageEntity packageEntity) {
        return null;
    }

    @Override
    public void delete(PackageEntity packageEntity) {

    }
}
