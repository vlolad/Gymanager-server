package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDtoShort {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
}
