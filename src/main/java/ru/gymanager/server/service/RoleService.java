package ru.gymanager.server.service;

import ru.gymanager.server.model.UserEntity;

public interface RoleService {

    UserEntity setRoleToUser(String userLogin, String roleName);

    UserEntity deleteRoleFromUser(String userId, String roleName);
}
