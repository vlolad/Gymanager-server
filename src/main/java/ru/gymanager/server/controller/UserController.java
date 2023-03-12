package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.model.UserData;
import ru.gymanager.server.model.UserInfoDto;
import ru.gymanager.server.repository.UserRepository;

@RestController
@Slf4j
public class UserController {

    private final UserRepository repo;

    @Autowired
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/user")
    public UserData getUser(@RequestParam String login) {
        return repo.findByLogin(login);
    }

    @GetMapping("/user/info")
    public UserInfoDto getUserInfo(@RequestParam String login) {
        return repo.findAllByLogin(login);
    }
}
