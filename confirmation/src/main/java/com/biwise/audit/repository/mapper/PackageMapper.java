package com.biwise.audit.repository.mapper;

import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PackageMapper implements RowMapper<PackageEntity> {
    @Override
    public PackageEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setEmail(resultSet.getString("email"));
        packageEntity.setPackageName(resultSet.getString("package_name"));
        packageEntity.setId(resultSet.getLong("id"));
        packageEntity.setPackageId(resultSet.getString("package_id"));
        return packageEntity;
    }
}
