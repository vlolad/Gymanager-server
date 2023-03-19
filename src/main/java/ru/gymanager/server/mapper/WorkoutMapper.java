package ru.gymanager.server.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.gymanager.server.dto.WorkoutDto;
import ru.gymanager.server.dto.WorkoutShortDto;
import ru.gymanager.server.model.WorkoutEntity;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ExerciseMapper.class})
public interface WorkoutMapper {

    WorkoutDto toDto(WorkoutEntity entity);
    WorkoutShortDto toShortDto(WorkoutEntity entity);
    List<WorkoutShortDto> toShortDtoList(List<WorkoutEntity> workoutEntityList);
}
