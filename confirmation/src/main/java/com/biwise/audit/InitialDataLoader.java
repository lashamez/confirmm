package com.biwise.audit;

import com.biwise.audit.domain.entity.PrivilegeEntity;
import com.biwise.audit.domain.entity.RoleEntity;
import com.biwise.audit.domain.entity.UserEntity;
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
        if (alreadySetup)
            return;
        PrivilegeEntity readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        if (!userRepository.findByEmail("admin").isPresent()) return;
        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
        UserEntity admin = new UserEntity();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPassword(passwordEncoder.encode("test"));
        admin.setEmail("admin");
        admin.setRoles(Collections.singletonList(adminRole));
        admin.setEnabled(true);
        userRepository.save(admin);

        UserEntity user = new UserEntity();
        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        user.setFirstName("user");
        user.setLastName("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user");
        user.setRoles(Collections.singletonList(userRole));
        user.setEnabled(true);
        userRepository.save(user);
        alreadySetup = true;
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
    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}