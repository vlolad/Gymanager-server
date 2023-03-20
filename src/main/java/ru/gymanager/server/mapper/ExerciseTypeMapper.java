package ru.gymanager.server.mapper;

import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.gymanager.server.dto.ExerciseTypeDto;
import ru.gymanager.server.model.ExerciseTypeEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {MeasureTypeMapper.class})
public interface ExerciseTypeMapper {
    ExerciseTypeDto toDto(ExerciseTypeEntity entity);
    List<ExerciseTypeDto> toDtoList(List<ExerciseTypeEntity> entityList);
}
