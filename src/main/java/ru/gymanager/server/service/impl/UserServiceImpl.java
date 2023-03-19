package ru.gymanager.server.service.impl;

import javax.xml.bind.ValidationException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.exception.BadRequestException;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.RoleEntity;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.UserLoginId;
import ru.gymanager.server.repository.RoleRepository;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

import java.util.*;

@Service
@Slf4j
// TODO сделать отдельную реализацию RoleService
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
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new NotFoundException("User with login=" + login + " not found.");
        }
        return user.get();
    }

    @Override
    public UserLoginId findTrainerInfo(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public UserEntity createUser(UserInfoDto userDto) {
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
    @Transactional
    public UserEntity updateUser(UserInfoDto updateDto) {
        UserEntity user = findAndValidateUserById(updateDto.getId());
        if (!StringUtils.isBlank(updateDto.getLogin())) {
            user.setLogin(updateDto.getLogin());
        }
        if (!StringUtils.isBlank(updateDto.getFirstName())) {
            user.setFirstName(updateDto.getFirstName());
        }
        if (!StringUtils.isBlank(updateDto.getMiddleName())) {
            user.setMiddleName(updateDto.getMiddleName());
        }
        if (!StringUtils.isBlank(updateDto.getLastName())) {
            user.setLastName(updateDto.getLastName());
        }
        if (!StringUtils.isBlank(updateDto.getPassword())) {
            log.warn("Change password foe user login={}", user.getLogin());
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        if (!StringUtils.isBlank(updateDto.getPhone())) {
            user.setPhone(updateDto.getPhone());
        }
        if (!StringUtils.isBlank(updateDto.getEmail())) {
            user.setEmail(updateDto.getEmail());
        }
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity createRole(String roleName) throws ValidationException {
        if (StringUtils.isBlank(roleName)) {
            throw new ValidationException("Role name is empty!");
        }
        Optional<RoleEntity> check = roleRepository.findByRole(RoleEntity.Role.valueOf(roleName));
        if (check.isPresent()) {
            return check.get();
        }
        RoleEntity role = new RoleEntity(roleName);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void setRoleToUser(String userId, String roleName) {
        UserEntity user = findAndValidateUserById(userId);
        if (user.getRoles().stream().anyMatch(role -> StringUtils.equals(roleName, role.getRole().name()))) {
            return;
        }

        RoleEntity role = findAndValidateRole(roleName);
        user.getRoles().add(role);
        log.warn("Set role {} to user (login={})", roleName, user.getLogin());
    }

    @Override
    @Transactional
    public void deleteRoleFromUser(String userId, String roleName) {
        UserEntity user = findAndValidateUserById(userId);
        log.info("User found with id={}", user.getId());
        RoleEntity role = findAndValidateRole(roleName);
        if (!user.getRoles().contains(role)) {
            log.warn("User (id={}) don't have role={}", user.getLogin(), roleName);
            throw new BadRequestException("This user don't have this role");
        }
        user.getRoles().remove(role);
    }

    private UserEntity findAndValidateUserById(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id " + id + " not found!");
        }
        return user.get();
    }

    private RoleEntity findAndValidateRole(String roleName) {
        Optional<RoleEntity> role = roleRepository.findByRole(RoleEntity.Role.valueOf(roleName));
        if (role.isEmpty()) {
            throw new NotFoundException("Role with name " + roleName + " not found!");
        }
        return role.get();
    }
}
