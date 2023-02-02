package com.fii.backendapp.controller;

import com.fii.backendapp.algorithm.AssignAlgorithm;
import com.fii.backendapp.algorithm.Assignation;
import com.fii.backendapp.algorithm.Convertor;
import com.fii.backendapp.dto.AccordDto;
import com.fii.backendapp.dto.MatchingDto;
import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.preference.PreferenceService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MatchingController {

    private final AccordService accordService;
    private final UserService userService;
    private final ProposalService proposalService;
    private final PreferenceService preferenceService;

    @GetMapping("/matchings")
    public ResponseEntity<List<MatchingDto>> getAllAccords() {
        Convertor convertor = new Convertor(accordService, userService, proposalService, preferenceService);
        convertor.initialize();
        var studIds = convertor.getStudIds();
        var propIds = convertor.getPropIds();
        var c = convertor.getC();
        AssignAlgorithm algo = new AssignAlgorithm(studIds.size(), propIds.size(), c);
        var solution = algo.solve();
        List<MatchingDto> response = new ArrayList<>();
        Long studentId, proposalId;
        Assignation assignation;
        Double cost;
        for (int i = 0; i < solution.size(); ++i) {
            assignation = solution.get(i);
            studentId = studIds.get(i);
            proposalId = propIds.get(assignation.getEnd());
            cost = assignation.getCost();
            response.add(new MatchingDto(studentId, proposalId, cost));
        }
        return ResponseEntity.ok().body(response);
    }
}
