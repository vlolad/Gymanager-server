package ru.gymanager.server.service;

import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserUpdateDto;

public interface UserService {
    UserEntity getUserByLogin(String login);

    UserEntity createUser(UserCreationDto userDto);

    UserEntity updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(String userId);
}
