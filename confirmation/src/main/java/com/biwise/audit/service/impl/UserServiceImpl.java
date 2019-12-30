package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.domain.entity.RoleEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.UserDao;
import com.biwise.audit.service.MailService;
import com.biwise.audit.service.UserService;
import com.biwise.audit.ui.errors.EmailAlreadyUsedException;
import com.biwise.audit.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {
    private static final int TOKEN_LENGTH = 50;

    private ModelMapper modelMapper = new ModelMapper();
    private final UserDao userDao;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Utils utils;

    private final MailService mailService;
    public UserServiceImpl( UserDao userDao, BCryptPasswordEncoder passwordEncoder, Utils utils, MailService mailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
        this.mailService = mailService;
    }

    public List<UserDto> findAll() {
        List<UserDto> returnValue = new ArrayList<>();
        userDao.findAll().forEach(userEntity -> returnValue.add(modelMapper.map(userEntity, UserDto.class)));
        return returnValue;
    }

    @Override
    public void delete(String id) {
        userDao.deleteByUserId(id);
    }

    @Override
    public UserDto findOne(String username) {
        Optional<UserEntity> userEntity = userDao.findByEmail(username);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    public UserDto findByUserId(String userId) {
        Optional<UserEntity> optionalUser = userDao.findByUserId(userId);
        Optional<UserDto> returnValue = optionalUser.map(userEntity -> modelMapper.map(userEntity, UserDto.class));
        return returnValue.orElse(null);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> user = userDao.findByUserId(userDto.getUserId());
        if(user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            UserEntity entity = userDao.save(userEntity);
            return modelMapper.map(entity, UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto save(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        UserEntity updated = userDao.save(userEntity);
        return modelMapper.map(updated, UserDto.class);
    }

    @Override
    public UserDto findByActivationKey(String token) {
        Optional<UserEntity> userEntity = userDao.findByActivationKey(token);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto register(UserDto user) {
        userDao.findByEmail(user.getEmail().toLowerCase()).ifPresent(existingUser->{
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        String token = utils.generateConfirmationToken(TOKEN_LENGTH);
        user.setActivationKey(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity newUser = modelMapper.map(user, UserEntity.class);
        newUser.setUserId(utils.generateUserId(30));
        newUser.setUsername(user.getUsername());
        newUser.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        UserEntity createdUser = userDao.save(newUser);
        UserDto saved = modelMapper.map(createdUser, UserDto.class);
        mailService.sendActivationEmail(saved);
        return saved;
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<UserEntity> userEntity = userDao.findByUsername(username);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    public List<UserDto> findAllForPackage(PackageDto packageDto) {
        List<UserEntity> users = userDao.findAllByCurrentPlan(modelMapper.map(packageDto, PackageEntity.class));
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    private boolean removeNonActivatedUser(UserEntity existingUser) {
        if (existingUser.isEnabled()) {
            return false;
        }
        userDao.delete(existingUser);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<UserEntity> optionalUser = userDao.findByUsername(email);
        if (!optionalUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    new ArrayList<>());
        }
        UserEntity user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}