package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.Role;

/**
 * Represents a User entity extending BaseEntity, with fields for login credentials and role.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    /**
     * Constructs a User object with specified id, login credentials, and role.
     *
     * @param id       The unique identifier for the user.
     * @param login    The login username of the user.
     * @param password The password associated with the user's login.
     * @param role     The role of the user, defaults to Role.USER if not specified.
     */
    public User(Long id, String login, String password, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * The login username of the user.
     */
    private String login;

    /**
     * The password associated with the user's login.
     */
    private String password;

    /**
     * The role of the user, defaults to Role.USER if not specified during construction.
     */
    @Builder.Default
    private Role role = Role.USER;
}
