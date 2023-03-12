package ru.gymanager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.UserData;
import ru.gymanager.server.model.UserInfoDto;

public interface UserRepository extends JpaRepository<UserData, String> {
    UserData findByLogin(String login);
    UserInfoDto findAllByLogin(String login);
}
