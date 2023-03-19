package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import ru.gymanager.server.dto.ExerciseResultDto;
import ru.gymanager.server.model.ExerciseResultEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseResultMapper {
    ExerciseResultDto toDto(ExerciseResultEntity entity);
    List<ExerciseResultDto> toDtoList(List<ExerciseResultEntity> entityList);
}
