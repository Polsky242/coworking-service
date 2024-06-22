package ru.polskiy;

import lombok.extern.slf4j.Slf4j;
import ru.polskiy.controller.MainController;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.NotValidArgumentException;
import ru.polskiy.exception.RegisterException;
import ru.polskiy.handler.AdminHandler;
import ru.polskiy.handler.MainHandler;
import ru.polskiy.in.Input;
import ru.polskiy.model.type.UserLocation;
import ru.polskiy.out.OutputData;

import static ru.polskiy.handler.AdminHandler.handleShowListOfRegisteredParticipants;
import static ru.polskiy.handler.SecurityHandler.handleAuthorization;
import static ru.polskiy.handler.SecurityHandler.handleRegistration;

/**
 * The ApplicationRunner class is responsible for running the main loop of the application.
 * It handles user input, manages different user locations, and delegates tasks to appropriate handlers.
 */
@Slf4j
public class ApplicationRunner {

    private static MainController controller;
    private static UserLocation userLocation;

    /**
     * The main entry point of the application. It loads the application context,
     * retrieves beans, and starts the main loop for processing user input.
     */
    public static void run() {
        ApplicationContext.loadContext();
        Input inputData = (Input) ApplicationContext.getBean("input");
        OutputData outputData = (OutputData) ApplicationContext.getBean("output");
        controller = (MainController) ApplicationContext.getBean("mainController");
        userLocation = UserLocation.SECURITY;

        boolean processIsRun = true;
        while (processIsRun) {
            try {
                switch (userLocation) {
                    case SECURITY -> handleSecurity(inputData, outputData);
                    case MAIN_MENU -> handleMenu(inputData, outputData);
                    case ADMIN_MENU -> handleAdmin(inputData, outputData);
                    case EXIT -> {
                        exitProcess(outputData);
                        processIsRun = false;
                    }
                }
            } catch (AuthorizeException |
                     NotValidArgumentException |
                     RegisterException e) {
                log.warn(e.getMessage());
                outputData.errOutput(e.getMessage());
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                outputData.errOutput("Unknown error. More details " + e.getMessage());
                processIsRun = false;
            }
        }
        inputData.closeIn();
    }

    /**
     * Handles the security stage where users can register, login, or exit the application.
     *
     * @param inputData  The input data provider.
     * @param outputData The output data provider.
     */
    public static void handleSecurity(Input inputData, OutputData outputData) {
        final String menu = """
                Введите одно число без пробелов или других символов:
                1. Регистрация.
                2. Вход в систему.
                3. Завершить программу.
                """;

        while (true) {
            outputData.output(menu);
            Object input = inputData.in();
            if (input.toString().equals("1")) {
                userLocation = handleRegistration(inputData, outputData, controller);
                break;
            } else if (input.toString().equals("2")) {
                userLocation = handleAuthorization(inputData, outputData, controller);
                break;
            } else if (input.toString().equals("3")) {
                userLocation = UserLocation.EXIT;
                break;
            } else {
                outputData.output("Неизвестная команда, повторите попытку.");
            }
        }
    }

    /**
     * Handles the admin stage where administrators can perform various actions.
     *
     * @param inputData  The input data provider.
     * @param outputData The output data provider.
     */
    private static void handleAdmin(Input inputData, OutputData outputData) {
        final String adminMessage = "Пожалуйста введите нужную вам команду.";
        final String adminMenu = """
                Введите одно число без пробелов и других символов:
                1. Получить список зарегистрированных участников.
                2. Получить весь список рабочих пространств.
                3. Добавить новый тип рабочего пространства.
                4. Добавить новое рабочее пространство.
                5. Выйти с аккаунта.
                6. Завершить программу.
                """;

        while (true) {
            outputData.output(adminMenu);
            outputData.output(adminMessage);
            Object input = inputData.in();

            if (input.equals("1")) {
                handleShowListOfRegisteredParticipants(outputData, controller);
            } else if (input.equals("2")) {
                AdminHandler.handleShowAllWorkspaces(outputData, controller);
                break;
            } else if (input.equals("3")) {
                AdminHandler.addNewWorkspaceType(inputData, outputData, controller);
                break;
            } else if (input.equals("4")) {
                AdminHandler.handleAddWorkspace(inputData, outputData, controller);
                break;
            } else if (input.equals("5")) {
                userLocation = UserLocation.SECURITY;
                break;
            } else if (input.equals("6")) {
                userLocation = UserLocation.EXIT;
                break;
            } else {
                outputData.output("Введите корректную команду!");
            }
        }
    }

    /**
     * Handles the main menu where users can view or book workspaces.
     *
     * @param inputData  The input data provider.
     * @param outputData The output data provider.
     */
    private static void handleMenu(Input inputData, OutputData outputData) {
        final String menuMessage = "Пожалуйста введите нужную вам команду.";
        final String menu = """
                Введите одно число без пробелов и других символов:
                1. Получение забронированных рабочих пространств.
                2. Бронирование.
                3. Просмотр свободных рабочих пространств за конкретный месяц.
                4. Просмотр всех рабочих пространств.
                5. Отменить бронь.
                6. Выйти с аккаунта.
                7. Завершить программу.
                """;

        outputData.output(menuMessage);
        while (true) {
            outputData.output(menu);
            Object input = inputData.in();
            if (input.equals("1")) {
                MainHandler.handleShowCurrentWorkspaces(outputData, controller);
            } else if (input.equals("2")) {
                MainHandler.handleWorkspaceSubmission(inputData, outputData, controller);
            } else if (input.equals("3")) {
                MainHandler.handleAvailableWorkspacesForSpecificDate(inputData, outputData, controller);
            } else if (input.equals("4")) {
                MainHandler.handleAvailableWorkspaces(outputData, controller);
            } else if (input.equals("5")) {
                MainHandler.bookCancel(inputData, outputData, controller);
            } else if (input.equals("6")) {
                userLocation = UserLocation.SECURITY;
                break;
            } else if (input.equals("7")) {
                userLocation = UserLocation.EXIT;
                break;
            } else {
                outputData.output("Введите корректную команду!");
            }
        }
    }

    /**
     * Handles the exit process, displaying a farewell message and cleaning up resources.
     *
     * @param outputData The output data provider.
     */
    private static void exitProcess(OutputData outputData) {
        final String message = "До свидания!";
        outputData.output(message);
        ApplicationContext.cleanAuthorizeUser();
    }
}
