package ru.gymanager.server.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gymanager.server.util.validate.Create;
import ru.gymanager.server.util.validate.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    @NotNull(groups = {Update.class})
    private String id;
    @NotNull(groups = {Create.class})
    private String login;
    @NotNull(groups = {Create.class})
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(groups = {Create.class})
    private String password;
    @NotNull(groups = {Create.class})
    private String phone;
    @Email(groups = {Create.class, Update.class})
    private String email;
    private List<String> roles;

    public UserInfoDto(String login, String password, String firstName, String email, String phone) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
