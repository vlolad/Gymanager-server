package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String description;
    private List<WorkoutShortDto> workouts;
}