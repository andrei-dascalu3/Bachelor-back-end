package com.fii.backendapp;

import com.fii.backendapp.model.user.Role;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.preference.PreferenceService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import com.fii.backendapp.util.Populator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackEndAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, ProposalService proposalService,
                          PreferenceService preferenceService, AccordService accordService) {
        return args -> {
            Populator populator = new Populator(userService, proposalService, preferenceService, accordService, 500, 2);
            populator.populate();
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
