package ru.polskiy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.dto.UserDto;
import ru.polskiy.model.entity.User;

@Mapper
public interface UserMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "role", source = "role")
    UserDto toDto(User entity);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);
}
