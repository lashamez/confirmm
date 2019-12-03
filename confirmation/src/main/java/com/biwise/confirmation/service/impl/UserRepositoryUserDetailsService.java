package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.repository.UserRepository;
import com.biwise.confirmation.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByEmail(userName);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(
                "User '" + userName + "' not found");
    }
}
