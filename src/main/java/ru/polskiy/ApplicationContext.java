package ru.polskiy;

import ru.polskiy.controller.MainController;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.dao.WorkspaceTypeDAO;
import ru.polskiy.dao.impl.UserDAOimpl;
import ru.polskiy.dao.impl.WorkspaceDAOimpl;
import ru.polskiy.dao.impl.WorkspaceTypeDAOimpl;
import ru.polskiy.in.ConsoleInput;
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

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private static final Map<String, Object> CONTEXT=new HashMap<>();

    public static void loadContext(){
        loadDAOLayer();
        loadServiceLayer();
        loadControllers();
        loadInputOutputLayers();
    }
    public static void loadAuthorizeUser(User user) {
        CONTEXT.put("authorize", user);
    }

    public static void cleanAuthorizeUser() {
        CONTEXT.remove("authorize");
    }

    public static User getAuthorizeUser() {
        return (User) CONTEXT.get("authorize");
    }

    public static Object getBean(String beanName) {
        return CONTEXT.get(beanName);
    }

    private static void loadInputOutputLayers() {
        CONTEXT.put("input",new ConsoleInput());
        CONTEXT.put("output",new ConsoleOutputData());
    }

    private static void loadControllers() {
        MainController controller =new MainController((SecurityService) CONTEXT.get("securityService"),
                (WorkspaceService) CONTEXT.get("workspaceService"),
                (WorkspaceTypeService) CONTEXT.get("workspaceTypeService"),
                (UserService) CONTEXT.get("userService"));
        CONTEXT.put("mainController",controller);
    }

    private static void loadServiceLayer() {
        CONTEXT.put("securityService", new SecurityServiceImpl((UserDAO) CONTEXT.get("userDAO")));
        CONTEXT.put("userService", new UserServiceImpl((UserDAO) CONTEXT.get("userDAO")));
        CONTEXT.put("workspaceService",new WorkspaceServiceImpl((WorkspaceDAO) CONTEXT.get("workspaceDAO")));
        CONTEXT.put("workspaceTypeService", new WorkspaceTypeServiceImpl((WorkspaceTypeDAO) CONTEXT.get("workspaceTypeDAO")));
    }

    private static void loadDAOLayer() {
        CONTEXT.put("userDAO", new UserDAOimpl());
        CONTEXT.put("workspaceDAO", new WorkspaceDAOimpl());
        CONTEXT.put("workspaceTypeDAO", new WorkspaceTypeDAOimpl());
    }

}
