package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.repository.UserRepository;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    public List<UserDto> findAll() {
        List<UserDto> returnValue = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> returnValue.add(modelMapper.map(userEntity, UserDto.class)));
        return returnValue;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteByUserId(id);
    }

    @Override
    public UserDto findOne(String username) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    public UserDto findByUserId(String userId) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        Optional<UserDto> returnValue = optionalUser.map(userEntity -> modelMapper.map(userEntity, UserDto.class));
        return returnValue.orElse(null);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findByUserId(userDto.getUserId());
        if(user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            UserEntity entity = userRepository.save(userEntity);
            return modelMapper.map(entity, UserDto.class);
        }
        return null;
    }


    @Override
    public void sendInvitationToAccountant(String name, String email, String accountant) {

    }

    @Override
    public UserDto save(UserDto user) {
        UserEntity newUser = modelMapper.map(user, UserEntity.class);
        newUser.setUserId(utils.generateUserId(30));
        UserEntity createdUser = userRepository.save(newUser);
        return modelMapper.map(createdUser, UserDto.class);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);

        if (!userEntity.isPresent()) throw new UsernameNotFoundException(username);
        UserEntity user = userEntity.get();
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities());
    }
}