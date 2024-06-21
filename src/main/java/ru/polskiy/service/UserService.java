package ru.polskiy.service;

import ru.polskiy.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    List<User> showAll();
}
