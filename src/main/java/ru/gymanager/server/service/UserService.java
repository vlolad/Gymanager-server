package ru.gymanager.server.service;

import java.util.Optional;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserUpdateDto;

public interface UserService {
    Optional<UserEntity> getUserByLogin(String login);

    UserEntity createUser(UserCreationDto userDto);

    UserEntity updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(String userId);
}
