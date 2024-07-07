package ru.polskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.polskiy.dto.ExceptionResponse;
import ru.polskiy.dto.WorkspaceDto;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.ValidationParametersException;
import ru.polskiy.mapper.WorkspaceMapper;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.security.Auth;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceService;

import java.io.IOException;
import java.util.List;

@WebServlet("/workspace/current")
public class CurrentWorkspaces extends HttpServlet {

    private WorkspaceService workspaceService;
    private UserService userService;
    private WorkspaceMapper workspaceMapper;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        workspaceService = (WorkspaceService) getServletContext().getAttribute("workspaceService");
        userService = (UserService) getServletContext().getAttribute("userService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        workspaceMapper = (WorkspaceMapper) getServletContext().getAttribute("meterReadingMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Auth authentication = (Auth) getServletContext().getAttribute("authentication");

        if (authentication.isAuth()) {
            try {
                showCurrentWorkspaces(resp, authentication);
            } catch (ValidationParametersException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
            } catch (AuthorizeException e) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
            }
        }
    }

    private void showCurrentWorkspaces(HttpServletResponse resp, Auth authentication) throws IOException {
        String login = authentication.getLogin();
        if (login == null) throw new ValidationParametersException("Login parameter is null!");
        User user = userService.getUserByLogin(login);
        if (!authentication.getLogin().equals(user.getLogin()))
            throw new AuthorizeException("Incorrect credentials.");
        List<Workspace> currentWorkspaces = workspaceService.getCurrentWorkspaces(user.getId());

        List<WorkspaceDto> workspaceDtos = currentWorkspaces.stream()
                .map(workspaceMapper::toDto)
                .toList();

        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), workspaceDtos);

    }
}
