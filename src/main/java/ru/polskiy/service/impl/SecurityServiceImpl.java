package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.polskiy.annotations.Auditable;
import ru.polskiy.annotations.Loggable;
import ru.polskiy.dao.UserDao;
import ru.polskiy.dto.TokenResponse;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.InvalidCredentialsException;
import ru.polskiy.exception.NotValidArgumentException;
import ru.polskiy.exception.RegisterException;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.Role;
import ru.polskiy.security.JwtTokenUtil;
import ru.polskiy.service.SecurityService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final CoworkingUserDetails userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserDao userDAO;
    private final JwtTokenUtil jwtTokenUtils;

    @Auditable(actionType = ActionType.REGISTRATION, login = "@login")
    @Loggable
    @Override
    public User register(String login, String password) {
        if (login == null || password == null || login.isEmpty()
                || password.isEmpty() || login.isBlank() || password.isBlank()) {
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
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        newUser.onCreate();
        return userDAO.save(newUser);
    }

    @Auditable(actionType = ActionType.AUTHORIZATION,login = "@login")
    @Override
    public TokenResponse authorize(String login, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password)
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        String token = jwtTokenUtils.generateToken(userDetails);

        return new TokenResponse(token);
    }
}
