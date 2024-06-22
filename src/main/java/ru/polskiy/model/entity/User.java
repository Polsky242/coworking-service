package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{

    Long id;

    String login;

    String password;

    @Builder.Default
    Role role=Role.USER;

}
