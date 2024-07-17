package ru.polskiy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration class for customizing web MVC settings.
 * Implements {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}
 * to provide custom configurations for message converters.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures Jackson JSON message converters with indentation enabled.
     *
     * @param converters the list of HttpMessageConverter instances to configure
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}