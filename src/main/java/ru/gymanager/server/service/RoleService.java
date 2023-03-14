package ru.gymanager.server.service;

import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role createRole(String roleName);

    UserEntity setRoleToUser(String userLogin, String roleName);

    UserEntity deleteRoleFromUser(String userId, String roleName);
}
