package ru.polskiy.dao;

import ru.polskiy.model.entity.User;

import java.util.Optional;

public interface UserDao extends MainDao<Long, User> {

    Optional<User> findByLogin(String login);
    User update(User user);
}
