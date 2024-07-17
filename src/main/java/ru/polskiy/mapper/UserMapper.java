package ru.polskiy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.dto.UserDto;
import ru.polskiy.model.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link User} entities and {@link UserDto} DTOs.
 * This interface uses MapStruct to generate the implementation automatically at build time.
 */
@Mapper
public interface UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param entity the {@link User} entity to convert
     * @return the converted {@link UserDto}
     */
    @Mapping(target = "login", source = "login")
    @Mapping(target = "role", source = "role")
    UserDto toDto(User entity);

    /**
     * Converts a {@link UserDto} to a {@link User} entity.
     *
     * @param userDto the {@link UserDto} to convert
     * @return the converted {@link User} entity
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "role", defaultValue = "USER")
    User toEntity(UserDto userDto);
}

