package ru.gymanager.server.service;

import ru.gymanager.server.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserByLogin(String login);

    UserEntity createUser();
}
