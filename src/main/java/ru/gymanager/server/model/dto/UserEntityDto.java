package ru.gymanager.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDto {
    private String id;
    private String name;
    private String login;
    private String email;
    private List<String> roles;
}
