package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseTypeDto {
    private String id;
    private String systemName;
    private String caption;
    private String description;
    private MeasureTypeDto measure;
}
