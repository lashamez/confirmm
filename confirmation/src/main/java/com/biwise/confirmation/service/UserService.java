package com.biwise.confirmation.service;

import com.biwise.confirmation.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);
    List<UserDto> findAll();
    void delete(String userId);

    UserDto findOne(String username);

    UserDto findByUserId(String userId);

    UserDto update(UserDto userDto);
}