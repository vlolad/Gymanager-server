package ru.gymanager.server.service.impl;

import javax.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserCreationDto;
import ru.gymanager.server.repository.RoleRepository;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

import java.util.*;

@Service
@Slf4j
// TODO сделать отедльную реализацию RoleService
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
        if (StringUtils.isBlank(login)) {
            return null;
        }
        Optional<UserEntity> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }

    @Override
    public UserEntity createUser(UserCreationDto userDto) {
        Optional<UserEntity> check = userRepository.findByLogin(userDto.getLogin());
        if (check.isPresent()) {
            return check.get();
        }
        UserEntity user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.info("User created with login: {}", user.getLogin());
        return userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(String roleName) throws ValidationException {
        if (StringUtils.isBlank(roleName)) {
            throw new ValidationException("Role name is empty!");
        }
        Optional<Role> check = roleRepository.findByName(roleName);
        if (check.isPresent()) {
            return check.get();
        }
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    @Override
    public void setRoleToUser(String userLogin, String roleName) {
        UserEntity user = findAndValidateUser(userLogin);
        if (user.getRoles().stream().anyMatch(role -> StringUtils.equals(roleName, role.getName()))) {
            return;
        }

        Role role = findAndValidateRole(roleName);
        user.getRoles().add(role);
        log.warn("Set role {} to user (login={})", roleName, userLogin);
    }

    private UserEntity findAndValidateUser(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new NotFoundException("User with login " + login + " not found!");
        }
        return user.get();
    }

    private Role findAndValidateRole(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new NotFoundException("Role with name " + roleName + " not found!");
        }
        return role.get();
    }
}
