package com.fii.backendapp.api;

import com.fii.backendapp.domain.proposal.Proposal;
import com.fii.backendapp.domain.user.User;
import com.fii.backendapp.service.proposal.ProposalService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProposalController {

    private final ProposalService proposalService;

    @GetMapping("/proposals")
    public ResponseEntity<List<Proposal>> getAllProposals() {
        return ResponseEntity.ok().body(proposalService.getAllProposals());
    }

    @PostMapping("/proposal/save")
    public ResponseEntity<Proposal> saveProposal(@RequestBody Proposal proposal) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/proposal/save").toUriString());
        return ResponseEntity.created(uri).body(proposalService.saveProposal(proposal));
    }
}
