package com.biwise.confirmation.service;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.UserRest;
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
}