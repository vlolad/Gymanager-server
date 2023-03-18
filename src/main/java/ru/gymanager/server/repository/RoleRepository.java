package ru.gymanager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.RoleEntity;
import ru.gymanager.server.model.RoleEntity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByRole(Role role);

}
