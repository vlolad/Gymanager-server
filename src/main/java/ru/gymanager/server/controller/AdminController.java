package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.dto.SimpleResponse;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserEntityDto;
import ru.gymanager.server.model.dto.UserUpdateDto;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/admin")
@Slf4j
@Validated
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntityDto createUser(@RequestBody UserCreationDto newUser) {
        log.info("Creating new user with login={}", newUser.getLogin());
        return userMapper.toUserEntityDto(userService.createUser(newUser));
    }

    @PatchMapping("/user/update")
    public UserEntityDto updateUser(@RequestBody UserUpdateDto updateDto) {
        log.info("Update user with id={}", updateDto.getId());
        return userMapper.toUserEntityDto(userService.updateUser(updateDto));
    }

    @DeleteMapping("/user/delete/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SimpleResponse deleteUser(@PathVariable @NotBlank String userId) {
        log.warn("Delete user with id={}", userId);
        userService.deleteUser(userId);
        return new SimpleResponse("User successfully delete");
    }

    @PatchMapping("/user/add/role/{userId}/{roleName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserEntityDto addRole(@PathVariable @NotBlank String userId, @PathVariable @NotBlank String roleName) {
        log.info("Add role={} to user id={}", roleName, userId);
        return userMapper.toUserEntityDto(roleService.setRoleToUser(userId, roleName));
    }

    @PatchMapping("/user/remove/role/{userId}/{roleName}")
    public UserEntityDto deleteRole(@PathVariable @NotBlank String userId, @PathVariable @NotBlank String roleName) {
        log.info("Remove role={} from user id={}", roleName, userId);
        return userMapper.toUserEntityDto(roleService.deleteRoleFromUser(userId, roleName));
    }
}
