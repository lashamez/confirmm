package com.biwise.audit.repository;

import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper = new ModelMapper();
    public UserDaoImpl(DataSource dataSource, UserMapper userMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserEntity> get(Long aLong) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from user where id = ?",
                new Object[]{aLong},
                userMapper
        ));
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
                    "(activation_key, email, enabled, first_name, lang_key, last_name, password, register_date, user_id, username, plan_id) " +
                    "values (?,?, ?,?,?,?,?,?,?,?,?);");
                    ps.setString(1, user.getActivationKey());
                    ps.setString(2, user.getEmail());
                    ps.setBoolean(3, user.isEnabled());
                    ps.setString(4, user.getFirstName());
                    ps.setString(5, user.getLangKey());
                    ps.setString(6, user.getLastName());
                    ps.setString(7, user.getPassword());
                    ps.setTimestamp(8, user.getRegisterDate());
                    ps.setString(9, user.getUserId());
                    ps.setString(10, user.getUsername());
                    ps.setLong(11, user.getCurrentPlan().getId());
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
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where username=?", new Object[]{username}, userMapper));
    }
    public Optional<UserEntity> findByActivationKey(String token) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where activation_key=?", new Object[]{token}, userMapper));
    }
    public Optional<UserEntity> findByUserId(String userId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where user_id=?", new Object[]{userId}, userMapper));
    }

    public void deleteByUserId(String userId) {
        jdbcTemplate.update("delete from user where user_id=?", userId);
    }
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from user where email=?", new Object[]{email}, userMapper));
    }

}
