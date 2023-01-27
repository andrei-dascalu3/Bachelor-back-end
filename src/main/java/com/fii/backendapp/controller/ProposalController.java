package com.fii.backendapp.controller;

import com.fii.backendapp.dto.ProposalDto;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProposalController {

    private final ProposalService proposalService;
    private final UserService userService;

    @GetMapping("/proposals")
    public ResponseEntity<List<ProposalDto>> getAllProposals() {
        List<Proposal> proposals = proposalService.getAllProposals();
        List<ProposalDto> proposalsDto = proposals.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(proposalsDto);
    }

    @GetMapping("/users/{id}/proposals")
    public ResponseEntity<List<ProposalDto>> getUserProposals(@PathVariable Long id,
                                                              @RequestParam("available") Optional<Boolean> available) {
        List<Proposal> proposals = new ArrayList<>();
        if (available.isPresent() && available.get()) {
            proposals = proposalService.getAvailableUserProposals(id);
        } else {
            proposals = proposalService.getUserProposals(id);
        }
        List<ProposalDto> proposalsDto = proposals.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(proposalsDto);
    }

    @PostMapping("/users/{id}/proposal/save")
    public ResponseEntity<ProposalDto> saveProposal(@RequestBody ProposalDto proposalDto, @PathVariable Long id) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/users/{id}/proposal/save")
                        .toUriString()
        );
        Proposal proposal = convertToEntity(proposalDto, id);
        Proposal proposalSaved = proposalService.saveProposal(proposal);
        return ResponseEntity.created(uri).body(convertToDto(proposalSaved));
    }

    @PutMapping("/users/{id}/proposals/{propId}/update")
    public ResponseEntity<ProposalDto> updateProposal(@RequestBody ProposalDto proposalDto, @PathVariable Long id,
                                                      @PathVariable Long propId) {
        Proposal proposal = proposalService.getProposal(propId);
        proposal.setTitle(proposalDto.getTitle());
        proposal.setDescription(proposalDto.getDescription());
        proposal.setPlaces(proposalDto.getPlaces());
        Proposal updatedProposal = proposalService.saveProposal(proposal);
        return ResponseEntity.ok().body(convertToDto(updatedProposal));
    }

    @DeleteMapping("/users/{id}/proposals/{propId}/delete")
    public ResponseEntity<Long> deleteProposal(@PathVariable Long id, @PathVariable Long propId) {
        Proposal proposal = proposalService.getProposal(propId);
        if (id != proposal.getAuthor().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        proposalService.deleteProposal(propId);
        return new ResponseEntity<>(propId, HttpStatus.NO_CONTENT);
    }

    private Proposal convertToEntity(ProposalDto proposalDto, Long profId) {
        Proposal proposal = new Proposal();
        proposal.setTitle(proposalDto.getTitle());
        proposal.setDescription(proposalDto.getDescription());
        proposal.setResources(proposalDto.getResources());
        proposal.setPlaces(proposalDto.getPlaces());
        User author = userService.getUser(profId);
        proposal.setAuthor(author);
        return proposal;
    }

    private ProposalDto convertToDto(Proposal proposal) {
        ProposalDto proposalDto = new ProposalDto();
        proposalDto.setId(proposal.getId());
        proposalDto.setTitle(proposal.getTitle());
        proposalDto.setDescription(proposal.getDescription());
        proposalDto.setResources(proposal.getResources());
        proposalDto.setPlaces(proposal.getPlaces());
        proposalDto.setProfId(proposal.getAuthor().getId());
        return proposalDto;
    }
}
