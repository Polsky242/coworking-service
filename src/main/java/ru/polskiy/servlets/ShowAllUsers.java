package ru.polskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.polskiy.dto.UserDto;
import ru.polskiy.exception.ValidationParametersException;
import ru.polskiy.mapper.UserMapper;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.security.Auth;
import ru.polskiy.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/all")
public class ShowAllUsers extends HttpServlet {

    private UserService userService;
    private UserMapper userMapper;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
        userMapper = (UserMapper) getServletContext().getAttribute("userMapper");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Auth authentication = (Auth) getServletContext().getAttribute("authentication");

        if (authentication.isAuth()) {
            try {
                showAllUsers(resp, authentication);
            } catch (ValidationParametersException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(e.getMessage());
            } catch (RuntimeException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(e.getMessage());
            }
        }
    }

    private void showAllUsers(HttpServletResponse resp, Auth authentication) throws IOException {
        Role role = authentication.getRole();
        String login = authentication.getLogin();

        if (login == null) {
            throw new ValidationParametersException("Login parameter is null!");
        }

        if (role.equals(Role.ADMIN)) {
            List<User> allUsers = userService.showAll();

            List<UserDto> allUserDto = allUsers.stream()
                    .map(userMapper::toDto)
                    .toList();

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), allUserDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("You are not admin to view all users.");
        }
    }
}
