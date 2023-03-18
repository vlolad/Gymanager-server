package ru.gymanager.server.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phone;

    public UserCreationDto(String login, String password, String firstName, String email, String phone) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
