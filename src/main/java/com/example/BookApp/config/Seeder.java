package com.example.BookApp.config;

import com.example.BookApp.model.UserEntity;
import com.example.BookApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class Seeder {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder encoder){
        return args -> {
            UserEntity admin = userRepository.findByName("admin")
                    .orElse(new UserEntity());

            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setCreatedAt(LocalDateTime.now());

            userRepository.save(admin);
        };
    }
}
