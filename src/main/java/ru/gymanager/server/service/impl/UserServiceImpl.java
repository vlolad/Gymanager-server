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

import java.util.List;
import java.util.Optional;

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
            return check.get();
        }
        UserEntity user = userMapper.toUserEntity(userDto);
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
        UserEntity user = findUser(userLogin);
        log.info("USER FOUND WITH ID={}", user.getId());
        Role role = findRole(roleName);
        user.getRoles().add(role);
        log.warn("Set role {} to user (login={})", roleName, userLogin);
        return userRepository.save(user);
    }

    private UserEntity findUser(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            return null; //TODO 404 exception
        }
        return user.get();
    }

    private Role findRole(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            return null; //TODO 404 exception
        }
        return role.get();
    }
}
