package ru.polskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.polskiy.dto.SuccessResponse;
import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.exception.ValidationParametersException;
import ru.polskiy.model.type.Role;
import ru.polskiy.security.Auth;
import ru.polskiy.service.WorkspaceTypeService;

import java.io.IOException;

@WebServlet("/workspace-type")
public class AddWorkspaceTypeServlet extends HttpServlet {

    private WorkspaceTypeService workspaceTypeService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        workspaceTypeService = (WorkspaceTypeService) getServletContext().getAttribute("workspaceTypeService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth authentication = (Auth) getServletContext().getAttribute("authentication");
        resp.setContentType("application/json");
        if (authentication.isAuth()) {
            try {
                addWorkspaceType(req, resp, authentication);
            } catch (ValidationParametersException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(e.getMessage());
            } catch (RuntimeException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(e.getMessage());
            }
        }
    }


    private void addWorkspaceType(HttpServletRequest req, HttpServletResponse resp, Auth authentication) throws IOException {
        Role role = authentication.getRole();
        String login = authentication.getLogin();

        if (login == null) {
            throw new ValidationParametersException("Login parameter is null!");
        }

        if (role.equals(Role.ADMIN)) {
            WorkspaceTypeRequest request = objectMapper.readValue(req.getInputStream(), WorkspaceTypeRequest.class);
            workspaceTypeService.save(request);


            resp.setStatus(HttpServletResponse.SC_OK);
            SuccessResponse successResponse = new SuccessResponse("The workspace type was successfully saved!");
            objectMapper.writeValue(resp.getWriter(), successResponse);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("You are not admin to set workspace type.");
        }
    }
}
