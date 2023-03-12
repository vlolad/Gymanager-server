package ru.gymanager.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;
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
                roleService.createRole("ADMIN");
                roleService.createRole("USER");
            }
            if (userService.getUserByLogin("test_admin") == null) {
                UserEntity user = userService.createUser(new UserCreationDto("Admin Adminovich", "test_admin",
                        "qwerty", "admin@null.org"));
                log.info("TEST USER CREATED WITH ID={}", user.getId());
                roleService.setRoleToUser("test_admin", "ADMIN");
            }
            if (userService.getUserByLogin("test_dummy") == null) {
                userService.createUser(new UserCreationDto("Ivan Vasya", "test_dummy",
                        "12345", "dummy@mail.ru"));
                roleService.setRoleToUser("test_dummy", "USER");
            }
        };
    }
}
