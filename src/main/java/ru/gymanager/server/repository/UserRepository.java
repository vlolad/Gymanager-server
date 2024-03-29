package ru.gymanager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.UserLoginId;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findById(String id);
    UserLoginId findUserByLogin(String login);
}
