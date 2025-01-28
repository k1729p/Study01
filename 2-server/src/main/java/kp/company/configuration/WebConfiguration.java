package kp.company.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The web configuration.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * The CORS configuration is set to allow all HTTP methods.<br>
     * The choice was done for sending the API requests to the localhost from
     * <b>Stoplight</b> page.
     *
     * @param registry the {@link CorsRegistry}
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
}
