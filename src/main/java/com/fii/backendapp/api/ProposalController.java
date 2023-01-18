package com.fii.backendapp.api;

import com.fii.backendapp.domain.proposal.Proposal;
import com.fii.backendapp.domain.user.User;
import com.fii.backendapp.service.proposal.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProposalController {

    private final ProposalService proposalService;

    @GetMapping("/proposals")
    public ResponseEntity<Set<Proposal>> getAllProposals() {
        return ResponseEntity.ok().body(proposalService.getAllProposals());
    }
}
