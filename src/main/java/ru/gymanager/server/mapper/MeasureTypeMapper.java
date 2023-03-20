package ru.gymanager.server.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.gymanager.server.dto.MeasureTypeDto;
import ru.gymanager.server.model.MeasureTypeEntity;

@Mapper(componentModel = "spring")
public interface MeasureTypeMapper {
    MeasureTypeDto toDto(MeasureTypeEntity entity);

    List<MeasureTypeDto> toDtoList(List<MeasureTypeEntity> entity);
}
