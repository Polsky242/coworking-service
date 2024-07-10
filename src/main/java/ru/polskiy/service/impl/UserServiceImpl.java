package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.UserDao;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDAO;

    @Override
    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> showAll() {
        return userDAO.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.findByLogin(login)
                .orElseThrow(() -> new NoSuchElementException("No user found with login: " + login));
    }
}
