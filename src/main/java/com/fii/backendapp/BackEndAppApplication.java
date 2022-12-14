package com.fii.backendapp;

import com.fii.backendapp.domain.Role;
import com.fii.backendapp.domain.User;
import com.fii.backendapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BackEndAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndAppApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveUser(new User(null, "John", "Smith", "john.smith@email.com", "password", false, new ArrayList<>()));
            userService.saveUser(new User(null, "Mike", "Jackson", "mike.jackson@email.com", "password", false, new ArrayList<>()));
            userService.saveUser(new User(null, "Ben", "Rope", "ben.rope@email.com", "password", false, new ArrayList<>()));
            userService.saveUser(new User(null, "Bob", "Miller", "bob.miller@email.com", "password", true, new ArrayList<>()));
            userService.saveUser(new User(null, "Tom", "Douglas", "tom.douglas@email.com", "password", true, new ArrayList<>()));

            userService.addRoleToUser("john.smith@email.com", "ROLE_USER");
            userService.addRoleToUser("mike.jackson@email.com", "ROLE_USER");
            userService.addRoleToUser("ben.rope@email.com", "ROLE_USER");
            userService.addRoleToUser("bob.miller@email.com", "ROLE_USER");
            userService.addRoleToUser("tom.douglas@email.com", "ROLE_USER");
            userService.addRoleToUser("tom.douglas@email.com", "ROLE_ADMIN");
        };
    }

}
