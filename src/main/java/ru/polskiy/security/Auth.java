package ru.polskiy.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.polskiy.model.type.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    private String login;
    private Role role;
    private boolean isAuth;
    private String message;
}
