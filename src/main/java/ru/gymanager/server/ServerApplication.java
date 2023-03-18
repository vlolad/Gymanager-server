package ru.gymanager.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gymanager.server.model.Role.DefaultRoles;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserCreationDto;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

@SpringBootApplication
@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            if (roleService.getAllRoles().isEmpty()) {
                roleService.createRole(DefaultRoles.ADMIN.name());
                roleService.createRole(DefaultRoles.USER.name());
            }
            UserEntity user = userService.createUser(
                new UserCreationDto(
                    "test_admin",
                    "qwerty",
                    "Admin Adminovich",
                    "admin@null.org",
                    "88889990011"
                )
            );
            roleService.setRoleToUser(user.getLogin(), DefaultRoles.ADMIN.name());
            // TODO test login to constant
            userService.createUser(new UserCreationDto(
                "test_user",
                "qwerty",
                "User",
                "user@null.org",
                "12223334455"
            ));
            roleService.setRoleToUser("test_user", DefaultRoles.USER.name());
        };
    }
}
