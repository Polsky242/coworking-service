package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.NotValidArgumentException;
import ru.polskiy.exception.RegisterException;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.service.SecurityService;

import java.util.Optional;

@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserDAO userDAO;

    @Override
    public User register(String login, String password) {
        if (login == null || password == null || login.isEmpty() || password.isEmpty() || login.isBlank() || password.isBlank()) {
            throw new NotValidArgumentException("логин и пароль не должны быть пустыми");
        }

        if (password.length() < 5) {
            throw new NotValidArgumentException("Длина пароля должна составлять не менее 5 символов.");
        }

        Optional<User> optionalUser = userDAO.findByLogin(login);
        if (optionalUser.isPresent()) {
            throw new RegisterException("Пользователь с таким логином уже существует.");
        }

        User newUser = User.builder()
                .login(login)
                .password(password)
                .role(Role.USER)
                .build();
        newUser.onCreate();
        return userDAO.save(newUser);

    }

    @Override
    public Optional<User> authorize(String login, String password) {
        Optional<User> user = userDAO.findByLogin(login);
        if(user.isEmpty()){
            throw new AuthorizeException("Нет пользователя с таким логином");
        }
        if(!user.get().getPassword().equals(password)){
            throw new AuthorizeException("Не правильный пароль");
        }
        return user;
    }
}
