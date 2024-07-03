package ru.polskiy;

import ru.polskiy.controller.MainController;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.dao.WorkspaceTypeDAO;
import ru.polskiy.dao.impl.UserDaoImpl;
import ru.polskiy.dao.impl.WorkspaceDaoImpl;
import ru.polskiy.dao.impl.WorkspaceTypeDaoImpl;
import ru.polskiy.in.ConsoleInput;
import ru.polskiy.liquibase.LiquibaseInstance;
import ru.polskiy.model.entity.User;
import ru.polskiy.out.ConsoleOutputData;
import ru.polskiy.service.SecurityService;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceService;
import ru.polskiy.service.WorkspaceTypeService;
import ru.polskiy.service.impl.SecurityServiceImpl;
import ru.polskiy.service.impl.UserServiceImpl;
import ru.polskiy.service.impl.WorkspaceServiceImpl;
import ru.polskiy.service.impl.WorkspaceTypeServiceImpl;
import ru.polskiy.util.ConnectionManager;
import ru.polskiy.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the application context and beans.
 *
 * This class is responsible for initializing and managing the various components
 * and services used in the application. It provides methods to load and access
 * beans and authorized users.
 */
public class ApplicationContext {

    private static final Map<String, Object> CONTEXT = new HashMap<>();
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    /**
     * Loads the application context.
     *
     * This method initializes the application context by loading properties,
     * DAO layer, service layer, controllers, and input/output layers.
     */
    public static void loadContext() {
        loadProperties();
        loadDAOLayer();
        loadServiceLayer();
        loadControllers();
        loadInputOutputLayers();
    }

    /**
     * Loads properties and initializes the connection manager.
     *
     * This method loads the database connection properties and initializes the
     * ConnectionManager and LiquibaseInstance. The connection manager is then
     * stored in the application context.
     */
    private static void loadProperties() {
        ConnectionManager connectionManager = new ConnectionManager(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY)
        );

        LiquibaseInstance liquibaseDemo = LiquibaseInstance.getInstance();
        liquibaseDemo.runMigrations(connectionManager.getConnection());

        CONTEXT.put("connectionManager", connectionManager);
    }

    /**
     * Loads the authorized user into the context.
     *
     * @param user The user to be authorized.
     */
    public static void loadAuthorizeUser(User user) {
        CONTEXT.put("authorize", user);
    }

    /**
     * Clears the authorized user from the context.
     */
    public static void cleanAuthorizeUser() {
        CONTEXT.remove("authorize");
    }

    /**
     * Retrieves the authorized user from the context.
     *
     * @return The authorized user, or null if no user is authorized.
     */
    public static User getAuthorizeUser() {
        return (User) CONTEXT.get("authorize");
    }

    /**
     * Retrieves a bean from the context by its name.
     *
     * @param beanName The name of the bean to retrieve.
     * @return The bean object associated with the specified name, or null if no such bean exists.
     */
    public static Object getBean(String beanName) {
        return CONTEXT.get(beanName);
    }

    /**
     * Loads the input and output layers into the context.
     */
    private static void loadInputOutputLayers() {
        CONTEXT.put("input", new ConsoleInput());
        CONTEXT.put("output", new ConsoleOutputData());
    }

    /**
     * Loads the controllers into the context.
     */
    private static void loadControllers() {
        MainController controller = new MainController(
                (SecurityService) CONTEXT.get("securityService"),
                (WorkspaceService) CONTEXT.get("workspaceService"),
                (WorkspaceTypeService) CONTEXT.get("workspaceTypeService"),
                (UserService) CONTEXT.get("userService")
        );
        CONTEXT.put("mainController", controller);
    }

    /**
     * Loads the service layer into the context.
     */
    private static void loadServiceLayer() {
        CONTEXT.put("securityService", new SecurityServiceImpl((UserDAO) CONTEXT.get("userDAO")));
        CONTEXT.put("userService", new UserServiceImpl((UserDAO) CONTEXT.get("userDAO")));
        CONTEXT.put("workspaceService", new WorkspaceServiceImpl((WorkspaceDAO) CONTEXT.get("workspaceDAO")));
        CONTEXT.put("workspaceTypeService", new WorkspaceTypeServiceImpl((WorkspaceTypeDAO) CONTEXT.get("workspaceTypeDAO")));
    }

    /**
     * Loads the DAO layer into the context.
     */
    private static void loadDAOLayer() {
        CONTEXT.put("userDAO", new UserDaoImpl((ConnectionManager) CONTEXT.get("connectionManager")));
        CONTEXT.put("workspaceDAO", new WorkspaceDaoImpl((ConnectionManager) CONTEXT.get("connectionManager")));
        CONTEXT.put("workspaceTypeDAO", new WorkspaceTypeDaoImpl((ConnectionManager) CONTEXT.get("connectionManager")));
    }

}
