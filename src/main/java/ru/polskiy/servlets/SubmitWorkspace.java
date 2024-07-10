package ru.polskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.polskiy.dto.ExceptionResponse;
import ru.polskiy.dto.ResponseMessage;
import ru.polskiy.dto.WorkspaceRequest;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.DuplicateRecordException;
import ru.polskiy.exception.NotValidArgumentException;
import ru.polskiy.exception.ValidationParametersException;
import ru.polskiy.model.entity.User;
import ru.polskiy.security.Auth;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceService;

import java.io.IOException;

@WebServlet("/workspace")
public class SubmitWorkspace extends HttpServlet {
    private WorkspaceService workspaceService;
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        workspaceService = (WorkspaceService) getServletContext().getAttribute("meterReadingService");
        userService = (UserService) getServletContext().getAttribute("userService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth authentication = (Auth) getServletContext().getAttribute("authentication");
        resp.setContentType("application/json");
        if (authentication.isAuth()) {
            try {
                String login = authentication.getLogin();
                if (login == null) throw new ValidationParametersException("Login parameter is null!");
                User user = userService.getUserByLogin(login);
                WorkspaceRequest workspaceRequest = objectMapper.readValue(req.getInputStream(), WorkspaceRequest.class);
                workspaceService.submitWorkspace(user.getId(), workspaceRequest.workspaceTypeId(), workspaceRequest.workspaceId());
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectMapper.writeValue(resp.getWriter(), new ResponseMessage("The workspace was successfully saved!"));

            } catch (NotValidArgumentException | AuthorizeException | DuplicateRecordException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
            } catch (RuntimeException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
            }
        }
    }
}
