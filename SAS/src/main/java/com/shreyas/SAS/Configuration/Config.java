package com.shreyas.SAS.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "http://localhost:4200",
                                "http://localhost:8080",
                                "https://1jxrkqvp-5173.inc1.devtunnels.ms/"
                        )
                        .allowedMethods("*") // Allows GET, POST, PUT, DELETE, etc.
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

