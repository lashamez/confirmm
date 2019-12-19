package com.biwise.audit.repository.mapper;

import com.biwise.audit.domain.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getLong("id"));
        user.setActivationKey(resultSet.getString("activation_key"));
        user.setEmail(resultSet.getString("email"));
        user.setLangKey(resultSet.getString("lang_key"));
        user.setFirstName(resultSet.getString("fist_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setRegisterDate(resultSet.getDate("register_date"));
        user.setUserId(resultSet.getString("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
