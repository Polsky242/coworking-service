package ru.polskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.polskiy.dto.ExceptionResponse;
import ru.polskiy.dto.SecurityRequest;
import ru.polskiy.dto.TokenResponse;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.service.SecurityService;

import java.io.IOException;

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {

    private SecurityService securityService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            SecurityRequest request = objectMapper.readValue(req.getInputStream(), SecurityRequest.class);
            TokenResponse token = securityService.authorize(request.login(), request.password());

            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            objectMapper.writeValue(resp.getWriter(), token);
        } catch (AuthorizeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(e.getMessage()));
        }
    }
}
