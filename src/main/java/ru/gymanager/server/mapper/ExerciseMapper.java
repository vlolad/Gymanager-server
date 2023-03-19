package ru.gymanager.server.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.gymanager.server.dto.ExerciseDto;
import ru.gymanager.server.model.ExerciseEntity;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ExerciseTypeMapper.class, ExerciseResultMapper.class})
public interface ExerciseMapper {
    ExerciseDto toDto(ExerciseEntity entity);
    List<ExerciseDto> toDtoList(List<ExerciseEntity> entityList);

}
