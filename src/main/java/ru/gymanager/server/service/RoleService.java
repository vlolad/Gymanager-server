package ru.gymanager.server.service;

import ru.gymanager.server.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role createRole(String roleName);

}
