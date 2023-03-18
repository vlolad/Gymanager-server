package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseDto {
    private String id;
    private String note;
    private ExerciseTypeDto exerciseType;
    private List<ExerciseResultDto> results;

    public ExerciseDto(String id, String note, ExerciseTypeDto exerciseType) {
        this.id = id;
        this.note = note;
        this.exerciseType = exerciseType;
    }
}
