package ru.gymanager.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.repository.RoleRepository;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService, RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity getUserByLogin(String login) {
        log.info("Get user with login: {}", login);
        // TODO null check
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    @Override
    public UserEntity createUser(UserCreationDto userDto) {
        Optional<UserEntity> check = userRepository.findByLogin(userDto.getLogin());
        if (check.isPresent()) {
            // TODO add warn message
            return check.get();
        }
        UserEntity user = userMapper.toUserEntity(userDto);
        // TODO add fields check (not empty)
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.info("Create user with login: {}", user.getLogin());
        return userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoles() {
        log.warn("SERVICE: get all roles");
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(String roleName) {
        // TODO empty check roleName
        Optional<Role> check = roleRepository.findByName(roleName);
        if (check.isPresent()) {
            log.info("Role already exists.");
            return check.get();
        }
        Role role = new Role(roleName);
        log.warn("SERVICE: save new role: {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public UserEntity setRoleToUser(String userLogin, String roleName) {
        UserEntity user = findAndValidateUser(userLogin);
        // TODO check user role existence
        log.info("USER FOUND WITH ID={}", user.getId());
        Role role = findAndValidateRole(roleName);
        user.getRoles().add(role);
        log.warn("Set role {} to user (login={})", roleName, userLogin);
        return userRepository.save(user);
    }

    private UserEntity findAndValidateUser(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            return null; //TODO not found exception
        }
        return user.get();
    }

    private Role findAndValidateRole(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            return null; //TODO not found exception
        }
        return role.get();
    }
}
