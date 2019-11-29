package com.biwise.confirmation.service;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.repository.UserRepository;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "userService")
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
        Optional<UserEntity> userEntity = userRepository.findByUserName(username);
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
            userEntity.setAge(userDto.getAge());
            userEntity.setSalary(userDto.getSalary());
            UserEntity entity = userRepository.save(userEntity);
            return modelMapper.map(entity, UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto save(UserDto user) {
        UserEntity newUser = modelMapper.map(user, UserEntity.class);
        newUser.setUserId(utils.generateUserId(30));
        UserEntity createdUser = userRepository.save(newUser);
        return modelMapper.map(createdUser, UserDto.class);
    }
}