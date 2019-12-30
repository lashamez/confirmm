package com.biwise.audit.repository.impl;

import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.UserDao;
import com.biwise.audit.repository.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper = new ModelMapper();
    public UserRepositoryImpl(DataSource dataSource, UserMapper userMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserEntity> get(Long aLong) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from user where id = ?",
                    new Object[]{aLong},
                    userMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return jdbcTemplate.query("select * from user", userMapper);
    }

    @Override
    public UserEntity save(UserEntity user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into user " +
                    "(activation_key, email, enabled, first_name, last_name, password_hash, register_date, user_id, username, plan_id) " +
                    "values (?,?, ?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getActivationKey());
                    ps.setString(2, user.getEmail());
                    ps.setBoolean(3, user.isEnabled());
                    ps.setString(4, user.getFirstName());
                    ps.setString(5, user.getLastName());
                    ps.setString(6, user.getPassword());
                    ps.setTimestamp(7, user.getRegisterDate());
                    ps.setString(8, user.getUserId());
                    ps.setString(9, user.getUsername());
                    ps.setLong(10, user.getCurrentPlan().getId());
                    return ps;
            }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        // TODO: 12/19/19
        return null;
    }

    @Override
    public void delete(UserEntity userEntity) {
        jdbcTemplate.update("delete from user where id=?", userEntity.getId());
    }

    public List<UserEntity> findAllByCurrentPlan(PackageEntity packageEntity) {
        return jdbcTemplate.query("select * from user where plan_id=?", new Object[]{packageEntity.getId()}, userMapper);
    }

    public Optional<UserEntity> findByUsername(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where username=?", new Object[]{username}, userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    public Optional<UserEntity> findByActivationKey(String token) {
        System.out.println(token);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where activation_key=?", new Object[]{token}, userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    public Optional<UserEntity> findByUserId(String userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where user_id=?", new Object[]{userId}, userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteByUserId(String userId) {
        jdbcTemplate.update("delete from user where user_id=?", userId);
    }
    public Optional<UserEntity> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where email=?", new Object[]{email}, userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
