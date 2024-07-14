package ru.polskiy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Configuration class for initializing the web application context.
 * Implements {@link org.springframework.web.WebApplicationInitializer} to configure
 * the Spring DispatcherServlet and context loader listener programmatically.
 *
 * Configures Spring Web MVC using {@link org.springframework.web.servlet.config.annotation.EnableWebMvc},
 * enables AspectJ support with {@link org.springframework.context.annotation.EnableAspectJAutoProxy},
 * and scans for components in the package "ru.polskiy" using {@link org.springframework.context.annotation.ComponentScan}.
 *
 * Additionally, it configures a DispatcherServlet dynamically by adding it to the ServletContext,
 * mapping it to the root ("/") and setting it to load on startup with a priority of 1.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("ru.polskiy")
public class ContextInitializer implements WebApplicationInitializer {

    /**
     * Initializes the ServletContext with the configured Spring context and DispatcherServlet.
     *
     * @param servletContext the ServletContext of the web application
     * @throws ServletException if the initialization fails
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ContextInitializer.class);

        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}