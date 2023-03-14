package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.model.Role;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.model.dto.UserCreationDto;
import ru.gymanager.server.model.dto.UserEntityDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(UserCreationDto userDto);

    UserEntityDto toUserEntityDto(UserEntity entity);

    List<String> toRoleNameList(Collection<Role> roles);

    default String toRoleName(Role role) {
        return role.getName();
    }
}
