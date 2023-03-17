package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.repository.UserRepository;
import ru.gymanager.server.service.UserService;

@RestController
@Slf4j
public class UserController {

    private final UserRepository repo;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserEntity getUser(@RequestParam String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/user/info")
    public UserInfoDto getUserInfo(@RequestParam String login) {
        return repo.findAllByLogin(login);
    }
}
