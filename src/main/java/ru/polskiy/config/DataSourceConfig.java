package ru.polskiy.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.polskiy.util.YamlPropertySourceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Configuration class for configuring DataSource and Liquibase for the application.
 * This class uses properties defined in "application.yml" for configuring the DataSource
 * and Liquibase settings.
 */
@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class DataSourceConfig {

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.driver-class-name}")
    private String driver;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${liquibase.change-log}")
    private String changeLogFile;

    @Value("${liquibase.liquibase-schema}")
    private String schemaName;

    /**
     * Configures and returns a JdbcTemplate bean initialized with the DataSource.
     *
     * @return configured JdbcTemplate instance
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName.split("[^\\p{L}]")[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new JdbcTemplate(dataSource);
    }

    /**
     * Configures and returns a SpringLiquibase bean initialized with the DataSource and
     * Liquibase settings.
     *
     * @return configured SpringLiquibase instance
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setLiquibaseSchema(schemaName);
        liquibase.setChangeLog(changeLogFile);
        liquibase.setDataSource(jdbcTemplate().getDataSource());
        return liquibase;
    }
}