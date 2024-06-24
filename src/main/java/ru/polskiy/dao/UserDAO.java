package ru.polskiy.dao;

import ru.polskiy.model.entity.User;

import java.util.Optional;

public interface UserDAO extends MainDAO<Long, User>{

    Optional<User> findByLogin(String login);
    User update(User user);
}
