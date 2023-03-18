package ru.gymanager.server.controller;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.mapper.UserMapper;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(
        UserService userService,
        UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public UserInfoDto getUser(@RequestParam String login) {
        Optional<UserEntity> user = userService.getUserByLogin(login);
        return user.map(userMapper::toUserInfoDto).orElse(null);
    }

}
