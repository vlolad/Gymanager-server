package ru.gymanager.server.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gymanager.server.util.validate.Create;
import ru.gymanager.server.util.validate.Update;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    @NotNull(groups = {Update.class})
    private String id;
    @NotNull(groups = {Create.class})
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(groups = {Create.class})
    private String phone;
    private String description;
    private Date birthday;
    private List<WorkoutShortDto> workouts;
}
