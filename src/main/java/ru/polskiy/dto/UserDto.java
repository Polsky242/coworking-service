package ru.polskiy.dto;

import lombok.Data;
import ru.polskiy.model.type.Role;

/**
 * Data Transfer Object (DTO) representing a user.
 */
@Data
public class UserDto {

    /**
     * The login username of the user.
     */
    private String login;

    /**
     * The role of the user.
     */
    private Role role;
}