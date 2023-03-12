package ru.gymanager.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gymanager.server.service.RoleService;
import ru.gymanager.server.service.UserService;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            if (roleService.getAllRoles().isEmpty()) {
                roleService.createRole("ADMIN");
                roleService.createRole("USER");
            }
            if (userService.getUserByLogin("test_admin") == null) {

            }
        };
    }
}
