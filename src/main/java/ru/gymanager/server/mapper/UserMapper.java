package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //UserInfoDto toInfoDto(UserData user);
    @Mapping(target="password", ignore = true)
    UserEntity toUserEntity(UserCreationDto userDto);
}
