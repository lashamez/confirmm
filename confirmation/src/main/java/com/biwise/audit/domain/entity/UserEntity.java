package com.biwise.audit.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.*;

@Data
public class UserEntity implements UserDetails {
    private Long id;

    private String username;

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    @Size(min = 2, max = 10)
    private String langKey;

    @JsonIgnore
    private PackageEntity currentPlan;

    private Collection<RoleEntity> roles = new ArrayList<>();

    private boolean enabled;
    @JsonIgnore
    private String activationKey;

    private Timestamp registerDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

}