package com.fii.backendapp.controller;

import com.fii.backendapp.dto.AccordDto;
import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.model.accord.AccordKey;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AccordController {

    private final AccordService accordService;
    private final UserService userService;
    private final ProposalService proposalService;

    @GetMapping("/accords")
    public ResponseEntity<List<AccordDto>> getAllAccords() {
        List<Accord> accords = accordService.getAllAccords();
        List<AccordDto> accordsDto = accords.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(accordsDto);
    }

    @GetMapping("/students/{id}/accords")
    public ResponseEntity<List<AccordDto>> getStudentAccords(@PathVariable Long id) {
        List<Accord> accords = accordService.getStudentAccords(id);
        List<AccordDto> accordsDto = accords.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(accordsDto);
    }

    @GetMapping("/professors/{id}/accords")
    public ResponseEntity<List<AccordDto>> getProfessorAccords(@PathVariable Long id) {
        List<Accord> accords = accordService.getProfessorAccords(id);
        List<AccordDto> accordsDto = accords.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(accordsDto);
    }

    @PostMapping("/professors/{id}/accords/save")
    public ResponseEntity<AccordDto> saveAccord(@RequestBody AccordDto accordDto, @PathVariable Long id) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/professors/{id}/accords/save")
                        .toUriString()
        );
        Accord accord = convertToEntity(accordDto, id);
        accord.setAccepted(false);
        Accord accordSaved = accordService.saveAccord(accord);
        return ResponseEntity.created(uri).body(convertToDto(accordSaved));
    }

    @PutMapping("/students/{id}/accords/{profId}/update")
    public ResponseEntity<AccordDto> updateAccordStatus(@RequestBody AccordDto accordDto, @PathVariable Long id,
                                                        @PathVariable Long profId) {
        AccordKey key = new AccordKey(id, profId);
        Accord accord = accordService.getAccord(key);
        accord.setAccepted(accordDto.isAccepted());
        Accord updatedAccord = accordService.saveAccord(accord);
        return ResponseEntity.ok().body(convertToDto(updatedAccord));
    }

    @DeleteMapping("/professors/{id}/accords/{studId}/delete")
    public ResponseEntity<AccordKey> deleteAccord(@PathVariable Long id, @PathVariable Long profId) {
        AccordKey key = new AccordKey(id, profId);
        Accord accord = accordService.getAccord(key);
        accordService.deleteAccord(key);
        return new ResponseEntity<>(key, HttpStatus.NO_CONTENT);
    }

    private Accord convertToEntity(AccordDto accordDto, Long id) {
        Accord accord = new Accord();
        User professor = userService.getUser(accordDto.getProfId());
        accord.setProfessor(professor);
        User student = userService.getUser(accordDto.getStudId());
        accord.setStudent(student);
        Proposal proposal = proposalService.getProposal(accordDto.getPropId());
        accord.setProposal(proposal);
        accord.setAccepted(accordDto.isAccepted());
        return accord;
    }

    private AccordDto convertToDto(Accord accord) {
        AccordDto accordDto = new AccordDto();
        accordDto.setAccepted(accord.isAccepted());
        accordDto.setPropId(accord.getProposal().getId());
        accordDto.setProfId(accord.getProfessor().getId());
        accordDto.setStudId(accord.getStudent().getId());
        return accordDto;
    }
}
