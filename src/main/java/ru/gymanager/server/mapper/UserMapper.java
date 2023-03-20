package ru.gymanager.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gymanager.server.dto.UserInfoDto;
import ru.gymanager.server.model.RoleEntity;
import ru.gymanager.server.model.UserEntity;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(UserInfoDto userDto);

    UserInfoDto toUserInfoDto(UserEntity entity);

    List<String> toRoleNameList(Collection<RoleEntity> roles);

    default String toRoleName(RoleEntity role) {
        return role.getRole().name();
    }
}
