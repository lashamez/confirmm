package com.biwise.audit;

import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.domain.entity.RoleEntity;
import com.biwise.audit.repository.PrivilegeRepository;
import com.biwise.audit.repository.RoleRepository;
import com.biwise.audit.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    private final PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        PrivilegeEntity readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

    }

    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}
