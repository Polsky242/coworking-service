package ru.polskiy.service;

import ru.polskiy.dto.TokenResponse;
import ru.polskiy.model.entity.User;

import java.util.Optional;

public interface SecurityService{

    User register(String login, String password);

    TokenResponse authorize(String login, String password);
}
