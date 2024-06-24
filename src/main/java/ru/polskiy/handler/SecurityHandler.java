package ru.polskiy.handler;

import ru.polskiy.ApplicationContext;
import ru.polskiy.controller.MainController;
import ru.polskiy.in.Input;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.model.type.UserLocation;
import ru.polskiy.out.OutputData;
import ru.polskiy.wrapper.SecurityWrapper;

/**
 * Utility class for handling security-related operations such as user registration and authorization.
 */
public class SecurityHandler {

    /**
     * Handles user registration process.
     *
     * @param inputData  Input source for reading user credentials.
     * @param outputData Output destination for displaying messages.
     * @param controller Main controller for accessing registration functionality.
     * @return The location to navigate to after registration, typically {@link UserLocation#MAIN_MENU}.
     */
    public static UserLocation handleRegistration(Input inputData, OutputData outputData, MainController controller) {
        SecurityWrapper swRegister = askCredentials(inputData, outputData);
        User registeredUser = controller.register(swRegister.getLogin(), swRegister.getPassword());
        ApplicationContext.loadAuthorizeUser(registeredUser);
        return UserLocation.MAIN_MENU;
    }

    /**
     * Handles user authorization process.
     *
     * @param inputData  Input source for reading user credentials.
     * @param outputData Output destination for displaying messages.
     * @param controller Main controller for accessing authorization functionality.
     * @return The location to navigate to after authorization, depending on user role.
     * {@link UserLocation#ADMIN_MENU} if user is an admin, otherwise {@link UserLocation#MAIN_MENU}.
     */
    public static UserLocation handleAuthorization(Input inputData, OutputData outputData, MainController controller) {
        SecurityWrapper swAuthorize = askCredentials(inputData, outputData);
        User authorizedUser = controller.authorize(swAuthorize.getLogin(), swAuthorize.getPassword()).orElse(null);

        if (authorizedUser != null) {
            ApplicationContext.loadAuthorizeUser(authorizedUser);
            return isAdmin(authorizedUser) ? UserLocation.ADMIN_MENU : UserLocation.MAIN_MENU;
        }

        return UserLocation.MAIN_MENU;
    }

    /**
     * Prompt user for login credentials.
     *
     * @param inputData  Input source for reading user input.
     * @param outputData Output destination for displaying messages.
     * @return SecurityWrapper containing user-provided login and password.
     */
    private static SecurityWrapper askCredentials(Input inputData, OutputData outputData) {
        final String loginMsg = "Введите логин:";
        outputData.output(loginMsg);
        String login = inputData.in().toString();

        final String passMsg = "Введите пароль:";
        outputData.output(passMsg);
        String password = inputData.in().toString();

        return SecurityWrapper.builder()
                .login(login)
                .password(password)
                .build();
    }

    /**
     * Checks if the given user is an administrator.
     *
     * @param user The user to check.
     * @return {@code true} if the user has the admin role, {@code false} otherwise.
     */
    public static boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMIN);
    }
}
