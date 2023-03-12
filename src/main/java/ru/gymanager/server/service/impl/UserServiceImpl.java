package ru.gymanager.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.repository.RoleRepository;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUserByLogin(String login) {
        log.info("Get user with login: {}", login);
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    @Override
    public UserEntity createUser() {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        log.warn("SERVICE: get all roles");
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(String roleName) {
        Optional<Role> check = roleRepository.findByName(roleName);
        if (check.isPresent()) {
            log.info("Role already exists.");
            return check.get();
        }
        Role role = new Role(roleName);
        log.warn("SERVICE: save new role: {}", role.getName());
        return roleRepository.save(role);
    }
}
