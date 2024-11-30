package lk.sasax.GreenShadow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the URL "/images/**" to the file path where images are stored
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:\\Users\\Sasa_x\\Downloads\\Upload");  // Path where images are stored
    }
}
