package ru.gymanager.server.service;

import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.UserLoginId;

public interface UserService {
    UserEntity getUserByLogin(String login);

    UserEntity createUser(UserInfoDto userDto);

    UserEntity updateUser(UserInfoDto userUpdateDto);

    void deleteUser(String userId);

    UserLoginId findTrainerInfo(String login);
}
