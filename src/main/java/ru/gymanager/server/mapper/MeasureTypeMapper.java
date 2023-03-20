package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import ru.gymanager.server.dto.MeasureTypeDto;
import ru.gymanager.server.model.MeasureTypeEntity;

@Mapper(componentModel = "spring")
public interface MeasureTypeMapper {
    MeasureTypeDto toDto(MeasureTypeEntity entity);
}
