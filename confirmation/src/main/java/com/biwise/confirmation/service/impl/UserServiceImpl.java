package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.entity.PrivilegeEntity;
import com.biwise.confirmation.domain.entity.RoleEntity;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.repository.RoleRepository;
import com.biwise.confirmation.repository.UserRepository;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.errors.EmailAlreadyUsedException;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, Utils utils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

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
    public UserDto save(UserDto user) {
        userRepository.findByEmail(user.getEmail().toLowerCase()).ifPresent(existingUser->{
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity newUser = modelMapper.map(user, UserEntity.class);
        newUser.setUserId(utils.generateUserId(30));
        UserEntity createdUser = userRepository.save(newUser);
        return modelMapper.map(createdUser, UserDto.class);
    }

    private boolean removeNonActivatedUser(UserEntity existingUser) {
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

        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByName("ROLE_USER"))));
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