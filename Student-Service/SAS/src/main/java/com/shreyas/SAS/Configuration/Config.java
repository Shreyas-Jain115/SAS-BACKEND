package com.shreyas.SAS.Configuration;
import com.shreyas.SAS.Entity.User;
import com.shreyas.SAS.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {
//    @Bean
//    public CommandLineRunner initAdminUser(UserRepo userRepository,PasswordEncoder passwordEncoder) {
//        return args -> {
//            String adminEmail = "owner@example.com";
//            if (!userRepository.existsByEmail(adminEmail)) {
//                User admin = new User();
//                admin.setEmail(adminEmail);
//                admin.setPassword(passwordEncoder.encode("owner"));
//                admin.setRole("owner");
//                userRepository.save(admin);
//                System.out.println("✅ Admin user created");
//            } else {
//                System.out.println("ℹ️ Admin user already exists");
//            }
//        };
//    }


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(
//                                "http://localhost:8080"
//                        )
//                        .allowedMethods("*") // Allows GET, POST, PUT, DELETE, etc.
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}

