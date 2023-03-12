package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import ru.gymanager.server.model.UserData;
import ru.gymanager.server.model.UserInfoDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //UserInfoDto toInfoDto(UserData user);
}
