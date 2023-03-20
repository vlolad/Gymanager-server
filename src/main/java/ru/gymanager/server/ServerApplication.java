package ru.gymanager.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.model.RoleEntity.Role;
import ru.gymanager.server.model.UserEntity;
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
                roleService.createRole(Role.ADMIN.name());
                roleService.createRole(Role.USER.name());
            }
            UserEntity admin = userService.createUser(
                new UserInfoDto(
                    "test_admin",
                    "qwerty",
                    "Admin Adminovich",
                    "admin@null.org",
                    "88889990011"
                )
            );
            roleService.setRoleToUser(admin.getId(), Role.ADMIN.name());
            UserEntity user = userService.createUser(new UserInfoDto(
                "test_user",
                "qwerty",
                "User",
                "user@null.org",
                "12223334455"
            ));
            roleService.setRoleToUser(user.getId(), Role.USER.name());
        };
    }
}
