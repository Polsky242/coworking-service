package ru.polskiy.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.model.entity.User;

@UtilityClass
public class SecurityUtils {
    private SecurityContext securityContext;

    public boolean isValidLogin(String login) {
        if (securityContext.getAuthentication() == null) {
            securityContext = SecurityContextHolder.getContext();
        }
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) throw new AuthorizeException("Unauthorized!");
        User principal = (User) authentication.getPrincipal();
        return principal.getLogin().equals(login);
    }
}
