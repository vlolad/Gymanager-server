package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String description;
}
