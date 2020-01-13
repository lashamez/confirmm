package com.biwise.audit.service;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    void delete(String userId);

    UserDto findOne(String username);

    UserDto findByUserId(String userId);

    UserDto update(UserDto userDto);

    UserDto save(UserDto user);

    UserDto findByActivationKey(String token);

    UserDto register(UserDto userDto);

    UserDto findByAlias(String username);

    List<UserDto> findAllForPackage(PackageDto packageDto);
}
