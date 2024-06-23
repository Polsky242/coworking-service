package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.Role;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{

    public User(Long id, String login, String password, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }
    String login;

    String password;

    @Builder.Default
    Role role=Role.USER;

}
