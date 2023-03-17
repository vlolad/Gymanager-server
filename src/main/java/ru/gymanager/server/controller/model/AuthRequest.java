package ru.gymanager.server.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AuthRequest {

    @NotNull
    String login;
    @NotNull
    String password;
}
