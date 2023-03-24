package ru.gymanager.server.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.dto.ClientDtoShort;
import ru.gymanager.server.model.ClientEntity;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {WorkoutMapper.class})
public interface ClientMapper {
    ClientDto toDto(ClientEntity clientEntity);

    @Mapping(target = "workouts", ignore = true)
    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd") // без этого отказывается маппить
    ClientEntity toEntity(ClientDto clientDto);

    List<ClientDtoShort> toDtoShortList(List<ClientEntity> entityList);
    ClientDtoShort toDtoShort(ClientEntity entity);
}
