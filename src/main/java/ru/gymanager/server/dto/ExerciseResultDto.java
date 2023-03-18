package ru.gymanager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseResultDto {
    private String id;
    private Integer executionOrder;
    private Integer result;
}
