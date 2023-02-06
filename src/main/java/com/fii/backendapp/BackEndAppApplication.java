package com.fii.backendapp;

import com.fii.backendapp.algorithm.AssignAlgorithm;
import com.fii.backendapp.algorithm.Assignation;
import com.fii.backendapp.algorithm.Convertor;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.preference.PreferenceService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import com.fii.backendapp.util.PopulatingConfiguration;
import com.fii.backendapp.util.Populator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BackEndAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndAppApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService, ProposalService proposalService,
//                          PreferenceService preferenceService, AccordService accordService) {
//        return args -> {
//            PopulatingConfiguration configuration = new PopulatingConfiguration(500, 2, 3, 20, 10, 20, 1.2);
//            Populator populator = new Populator(userService, proposalService, preferenceService, accordService,
//                    configuration);
//            populator.populate();
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void printSolution(Map<Integer, Assignation> solution, List<Long> studIds, List<Long> propIds) {
        System.out.println(solution);
        for (var i : solution.keySet()) {
            var assignation = solution.get(i);
            System.out.println(studIds.get(i) + " -> " + propIds.get(assignation.getEnd()) + " with cost = " + assignation.getCost());
        }
    }

    private void testAlgo(Convertor convertor) {
        convertor.initialize();
        var studIds = convertor.getStudIds();
        var propIds = convertor.getPropIds();
        var c = convertor.getC();
        AssignAlgorithm algo = new AssignAlgorithm(studIds.size(), propIds.size(), c);
        var solution = algo.solve();
        printSolution(solution, studIds, propIds);
    }
}
