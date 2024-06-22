package ru.polskiy.handler;

import ru.polskiy.ApplicationContext;
import ru.polskiy.controller.MainController;
import ru.polskiy.in.Input;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.model.type.UserLocation;
import ru.polskiy.out.OutputData;
import ru.polskiy.wrapper.SecurityWrapper;

public class SecurityHandler {

    public static UserLocation handleRegistration(Input inputData, OutputData outputData, MainController controller) {
        SecurityWrapper swRegister = askCredentials(inputData, outputData);
        User registeredUser = controller.register(swRegister.getLogin(), swRegister.getPassword());
        ApplicationContext.loadAuthorizeUser(registeredUser);
        return UserLocation.MAIN_MENU;
    }

    public static UserLocation handleAuthorization(Input inputData, OutputData outputData, MainController controller) {
        SecurityWrapper swAuthorize = askCredentials(inputData, outputData);
        User authorizedUser = controller.authorize(swAuthorize.getLogin(), swAuthorize.getPassword()).orElse(null);

        if (authorizedUser != null) {
            ApplicationContext.loadAuthorizeUser(authorizedUser);
            return isAdmin(authorizedUser) ? UserLocation.ADMIN_MENU : UserLocation.MAIN_MENU;
        }

        return UserLocation.MAIN_MENU;
    }

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

    public static boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMIN);
    }
}
