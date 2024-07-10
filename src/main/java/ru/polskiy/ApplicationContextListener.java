package ru.polskiy;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.mapstruct.factory.Mappers;
import ru.polskiy.aspects.AuditAspect;
import ru.polskiy.dao.UserDao;
import ru.polskiy.dao.WorkspaceDao;
import ru.polskiy.dao.WorkspaceTypeDao;
import ru.polskiy.dao.impl.AuditDaoImpl;
import ru.polskiy.dao.impl.UserDaoImpl;
import ru.polskiy.dao.impl.WorkspaceDaoImpl;
import ru.polskiy.dao.impl.WorkspaceTypeDaoImpl;
import ru.polskiy.liquibase.LiquibaseInstance;
import ru.polskiy.mapper.UserMapper;
import ru.polskiy.mapper.WorkspaceMapper;
import ru.polskiy.security.JwtTokenUtil;
import ru.polskiy.service.*;
import ru.polskiy.service.impl.*;
import ru.polskiy.util.ConnectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    private Properties properties;
    private ConnectionManager connectionManager;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        loadProperties(servletContext);
        databaseConfiguration(servletContext);
        serviceContextInit(servletContext);

        ObjectMapper objectMapper = new ObjectMapper();
        servletContext.setAttribute("objectMapper", objectMapper);
        servletContext.setAttribute("userMapper", Mappers.getMapper(UserMapper.class));
        servletContext.setAttribute("workspaceMapper", Mappers.getMapper(WorkspaceMapper.class));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    private void loadProperties(ServletContext servletContext) {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(servletContext.getResourceAsStream("/WEB-INF/classes/application.properties"));
                servletContext.setAttribute("servletProperties", properties);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Property file not found!");
            } catch (IOException e) {
                throw new RuntimeException("Error reading configuration file: " + e.getMessage());
            }
        }
    }

    private void databaseConfiguration(ServletContext servletContext) {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");

        connectionManager = new ConnectionManager(url, username, password, driver);
        servletContext.setAttribute("connectionManager", connectionManager);

        String changeLogFile = properties.getProperty("liquibase.changelog");
        String schemaName = properties.getProperty("liquibase.liquibase-schema");

        if (Boolean.parseBoolean(properties.getProperty("liquibase.enabled"))) {
            LiquibaseInstance liquibaseInstance = new LiquibaseInstance(connectionManager.getConnection(), changeLogFile, schemaName);
            liquibaseInstance.runMigrations();
            servletContext.setAttribute("liquibaseDemo", liquibaseInstance);
        }
    }

    private void serviceContextInit(ServletContext servletContext) {
        UserDao userDAO = new UserDaoImpl(connectionManager);
        WorkspaceTypeDao workspaceTypeDao = new WorkspaceTypeDaoImpl(connectionManager);
        WorkspaceDao workspaceDao = new WorkspaceDaoImpl(connectionManager);
        AuditDaoImpl auditDAO = new AuditDaoImpl(connectionManager);

        AuditService auditService = new AuditServiceImpl(auditDAO);
        UserService userService = new UserServiceImpl(userDAO);

        AuditAspect auditAspect = new AuditAspect(auditService);

        JwtTokenUtil jwtTokenUtils = new JwtTokenUtil(
                properties.getProperty("jwt.secret"),
                Duration.parse(properties.getProperty("jwt.lifetime")),
                userService
        );

        SecurityService securityService = new SecurityServiceImpl(userDAO, jwtTokenUtils);
        WorkspaceTypeService workspaceTypeService = new WorkspaceTypeServiceImpl(workspaceTypeDao);
        WorkspaceService workspaceService = new WorkspaceServiceImpl(workspaceDao);

        servletContext.setAttribute("jwtTokenUtils", jwtTokenUtils);
        servletContext.setAttribute("auditService", auditService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("securityService", securityService);
        servletContext.setAttribute("workspaceTypeService", workspaceTypeService);
        servletContext.setAttribute("workspaceService", workspaceService);
    }
}
