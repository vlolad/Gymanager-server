package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gymanager.server.dto.UserCreationDto;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.mapper.UserMapper;
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
    public UserInfoDto createUser(@RequestBody UserCreationDto newUser) {
        log.info("Creating new user with login={}", newUser.getLogin());
        return userMapper.toUserInfoDto(userService.createUser(newUser));
    }

    @PatchMapping("/user/update")
    public UserInfoDto updateUser(@RequestBody UserInfoDto updateDto) {
        log.info("Update user with id={}", updateDto.getId());
        return userMapper.toUserInfoDto(userService.updateUser(updateDto));
    }

    @DeleteMapping("/user/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable @NotBlank String userId) {
        log.warn("Delete user with id={}", userId);
        userService.deleteUser(userId);
    }

    @PatchMapping("/user/add/role/{userId}/{roleName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addRole(@PathVariable @NotBlank String userId, @PathVariable @NotBlank String roleName) {
        log.info("Add role={} to user id={}", roleName, userId);
        roleService.setRoleToUser(userId, roleName);
    }

    @PatchMapping("/user/remove/role/{userId}/{roleName}")
    public void deleteRole(@PathVariable @NotBlank String userLogin, @PathVariable @NotBlank String roleName) {
        log.info("Remove role={} from user id={}", roleName, userLogin);
        roleService.deleteRoleFromUser(userLogin, roleName);
    }
}
