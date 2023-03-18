package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(ClientEntity clientEntity);
}
