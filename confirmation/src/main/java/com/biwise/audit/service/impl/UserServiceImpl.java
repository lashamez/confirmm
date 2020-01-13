package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.domain.entity.RoleEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.RoleRepository;
import com.biwise.audit.repository.UserRepository;
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

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {
    private static final int TOKEN_LENGTH = 50;

    private ModelMapper modelMapper = new ModelMapper();

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Utils utils;

    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, Utils utils, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
        this.mailService = mailService;
    }

    public List<UserDto> findAll() {
        List<UserDto> returnValue = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> returnValue.add(modelMapper.map(userEntity, UserDto.class)));
        return returnValue;
    }

    @Override
    @Transactional
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
    @Transactional
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findByUserId(userDto.getUserId());
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            UserEntity entity = userRepository.save(userEntity);
            return modelMapper.map(entity, UserDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public UserDto save(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        UserEntity updated = userRepository.save(userEntity);
        return modelMapper.map(updated, UserDto.class);
    }

    @Override
    public UserDto findByActivationKey(String token) {
        Optional<UserEntity> userEntity = userRepository.findByActivationKey(token);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    @Transactional
    public UserDto register(UserDto user) {
        userRepository.findByEmail(user.getEmail().toLowerCase()).ifPresent(existingUser -> {
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
        newUser.setAlias(user.getAlias());
        UserEntity createdUser = userRepository.save(newUser);
        UserDto saved = modelMapper.map(createdUser, UserDto.class);
        mailService.sendActivationEmail(saved);
        return saved;
    }

    @Override
    public UserDto findByAlias(String username) {
        Optional<UserEntity> userEntity = userRepository.findByAlias(username);
        return userEntity.map(entity -> modelMapper.map(entity, UserDto.class)).orElse(null);
    }

    @Override
    public List<UserDto> findAllForPackage(PackageDto packageDto) {
        List<UserEntity> users = userRepository.findAllByCurrentPlan(modelMapper.map(packageDto, PackageEntity.class));
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Transactional
    boolean removeNonActivatedUser(UserEntity existingUser) {
        if (existingUser.isEnabled()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<UserEntity> optionalUser = userRepository.findByAlias(email);
        if (!optionalUser.isPresent()) {
            optionalUser = userRepository.findByEmail(email);
            if (!optionalUser.isPresent()) {
                return new org.springframework.security.core.userdetails.User(
                        " ", " ", true, true, true, true,
                        getAuthorities(Collections.singletonList(
                                roleRepository.findByName("ROLE_USER"))));
            }
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
