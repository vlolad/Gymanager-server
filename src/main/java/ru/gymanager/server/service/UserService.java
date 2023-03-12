package ru.gymanager.server.service;

import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;

import java.util.List;

public interface UserService {
    UserEntity getUserByLogin(String login);

    UserEntity createUser(UserCreationDto userDto);
}
