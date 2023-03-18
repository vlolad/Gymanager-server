package ru.gymanager.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gymanager.server.exception.BadRequestException;
import ru.gymanager.server.exception.NotFoundException;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserUpdateDto;
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
            log.warn("User with login={} already exists", userDto.getLogin());
            return check.get();
        }
        UserEntity user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.info("Create user with login: {}", user.getLogin());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserUpdateDto updateDto) {
        UserEntity user = findAndValidateUser(updateDto.getId());
        if (!updateDto.getName().isBlank()) {
            user.setName(updateDto.getName());
        }
        if (!updateDto.getLogin().isBlank()) {
            user.setLogin(updateDto.getLogin());
        }
        if (!updateDto.getPassword().isBlank()) {
            log.warn("Update user password (id={})", updateDto.getId());
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        if (!updateDto.getEmail().isBlank()) {
            user.setEmail(updateDto.getEmail());
        }

        return user;
    }

    @Override
    public void deleteUser(String userId) {
        findAndValidateUser(userId);
        userRepository.deleteById(userId);
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
    @Transactional
    public UserEntity setRoleToUser(String userId, String roleName) {
        UserEntity user = findAndValidateUser(userId);
        log.info("User found with id={}", user.getId());
        Role role = findAndValidateRole(roleName);
        if (user.getRoles().contains(role)) {
            log.warn("User (id={}) already have role={}", userId, roleName);
            throw new BadRequestException("This user already have this role");
        }
        user.getRoles().add(role);
        log.warn("Set role {} to user (id={})", roleName, userId);
        return user;
    }

    @Override
    @Transactional
    public UserEntity deleteRoleFromUser(String userId, String roleName) {
        UserEntity user = findAndValidateUser(userId);
        log.info("User found with id={}", user.getId());
        Role role = findAndValidateRole(roleName);
        if (!user.getRoles().contains(role)) {
            log.warn("User (id={}) don't have role={}", userId, roleName);
            throw new BadRequestException("This user don't have this role");
        }
        user.getRoles().remove(role);
        return user;
    }

    private UserEntity findAndValidateUser(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.warn("User with id={} not found", userId);
            throw new NotFoundException("User with id " + userId + " not found.");
        }
        return user.get();
    }

    private Role findAndValidateRole(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            log.warn("Role with name={} not found", roleName);
            throw new NotFoundException("Role with name " + roleName + " not found.");
        }
        return role.get();
    }
}
