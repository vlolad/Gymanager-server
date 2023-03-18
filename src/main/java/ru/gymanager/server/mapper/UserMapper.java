package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.dto.UserCreationDto;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(UserCreationDto userDto);

    UserInfoDto toUserInfoDto(UserEntity entity);

    List<String> toRoleNameList(Collection<Role> roles);

    default String toRoleName(Role role) {
        return role.getName();
    }
}
