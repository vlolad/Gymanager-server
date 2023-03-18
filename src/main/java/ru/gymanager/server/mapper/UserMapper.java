package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.dto.UserCreationDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoDto toUserInfoDto(UserEntity user);

    UserEntity toUserEntity(UserCreationDto userDto);
}
