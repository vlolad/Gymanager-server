package ru.gymanager.server.service;

import javax.xml.bind.ValidationException;
import ru.gymanager.server.model.RoleEntity;

import java.util.List;

public interface RoleService {
    List<RoleEntity> getAllRoles();

    RoleEntity createRole(String roleName) throws ValidationException;

    void setRoleToUser(String userLogin, String roleName);

    void deleteRoleFromUser(String userId, String roleName);
}
