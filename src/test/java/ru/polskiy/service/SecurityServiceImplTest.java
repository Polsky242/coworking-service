package ru.polskiy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.RegisterException;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.impl.SecurityServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityService;
    @Mock
    private UserDAO userDAO;

    @Test
    @DisplayName("check if registration of new user success")
    void testRegister_Success() {
        String login = "login";
        String password = "password";
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        Mockito.when(userDAO.findByLogin(login)).thenReturn(Optional.empty());
        Mockito.when(userDAO.save(any(User.class))).thenReturn(user);

        User registerUser = securityService.register(login, password);
        assertEquals(login, registerUser.getLogin());
        assertEquals(password, registerUser.getPassword());
    }

    @Test
    @DisplayName("check if registration throws RegisterExcepton")
    void testRegister_ThrowException() {
        String login = "login";
        String password = "password";
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        Mockito.when(userDAO.findByLogin(login)).thenReturn(Optional.of(user));

        assertThrows(RegisterException.class, () -> securityService.register(login, password));
    }

    @Test
    void testAuthorization_Success() {
        String login = "login";
        String password = "password";
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        Mockito.when(userDAO.findByLogin(login)).thenReturn(Optional.of(user));

        Optional<User> authorization = securityService.authorize(login, password);
        assertEquals(login, authorization.get().getLogin());
        assertEquals(password, authorization.get().getPassword());
    }

    @Test
    @DisplayName("if user is empty authorization throws AuthorizeException")
    void testAuthorization_ThrowException() {
        String login = "login";
        String password = "password";
        Mockito.when(userDAO.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(AuthorizeException.class, () -> securityService.authorize(login, password));
    }
}
