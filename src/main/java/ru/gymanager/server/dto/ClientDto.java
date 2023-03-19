package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gymanager.server.util.validate.Create;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private String id;
    @NotNull(groups = {Create.class})
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(groups = {Create.class})
    private String phone;
    private String description;
    private List<WorkoutShortDto> workouts;
}
