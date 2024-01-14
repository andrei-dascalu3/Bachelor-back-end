package com.fii.backendapp.controller;

import com.fii.backendapp.algorithm.AssignAlgorithm;
import com.fii.backendapp.algorithm.Assignation;
import com.fii.backendapp.algorithm.Convertor;
import com.fii.backendapp.dto.matching.MatchingDto;
import com.fii.backendapp.dto.matching.MatchingProposalDto;
import com.fii.backendapp.dto.matching.MatchingStudentDto;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
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
import java.util.Map;

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
    public ResponseEntity<List<MatchingDto>> getAllMatchings() {
        Convertor convertor = new Convertor(accordService, userService, proposalService, preferenceService);
        convertor.initialize();
        // get ids indexed
        var studIds = convertor.getStudIds();
        var propIds = convertor.getPropIds();
        var accordIds = convertor.getAccordIds();

        // get cost matrix
        var c = convertor.getC();

        // solve
        AssignAlgorithm algo = new AssignAlgorithm(studIds.size(), propIds.size(), c);
        var solution = algo.solve();

        // converting solution to matchings
        var response = convertSolutionToResponse(solution, studIds, propIds, accordIds);
        return ResponseEntity.ok().body(response);
    }

    private List<MatchingDto> convertSolutionToResponse(Map<Integer, Assignation> solution, List<Long> studIds,
                                                        List<Long> propIds, Map<Long, Long> accordIds) {
        List<MatchingDto> response = new ArrayList<>();
        Long studentId;
        Long proposalId;
        Assignation assignation;
        Double cost;

        // accords
        if(!accordIds.isEmpty()) {
            for (var entry : accordIds.entrySet()) {
                studentId = entry.getKey();
                proposalId = entry.getValue();
                cost = 0.0;
                response.add(convertSolutionToDto(studentId, proposalId, cost));
            }
        }
        // computed matchings
        for (int i = 0; i < solution.size(); ++i) {
            assignation = solution.get(i);
            studentId = studIds.get(i);
            proposalId = propIds.get(assignation.getEnd());
            cost = assignation.getCost();
            response.add(convertSolutionToDto(studentId, proposalId, cost));
        }
        return response;
    }

    private MatchingDto convertSolutionToDto(Long studentId, Long proposalId, Double cost) {
        User student = userService.getUser(studentId);
        Proposal proposal = proposalService.getProposal(proposalId);
        MatchingStudentDto matchingStudentDto = convertStudentToMatchingStudentDto(student);
        MatchingProposalDto matchingProposalDto = convertProposalToMatchingProposalDto(proposal);
        return new MatchingDto(matchingStudentDto, matchingProposalDto, cost);
    }

    private MatchingStudentDto convertStudentToMatchingStudentDto(User student) {
        return new MatchingStudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getUsername());
    }

    private MatchingProposalDto convertProposalToMatchingProposalDto(Proposal proposal) {
        return new MatchingProposalDto(
                proposal.getId(),
                proposal.getTitle(),
                proposal.getDescription(),
                proposal.getResources(),
                proposal.getAuthor().getUsername()
        );
    }
}
