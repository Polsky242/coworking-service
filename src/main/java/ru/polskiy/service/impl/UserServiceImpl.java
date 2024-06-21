package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> showAll() {
        return userDAO.findAll();
    }

}
