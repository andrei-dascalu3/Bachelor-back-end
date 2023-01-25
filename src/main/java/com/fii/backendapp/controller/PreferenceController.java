package com.fii.backendapp.controller;

import com.fii.backendapp.dto.PreferenceDto;
import com.fii.backendapp.dto.ProposalDto;
import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.preference.PreferenceKey;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.service.preference.PreferenceService;
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
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final UserService userService;
    private final ProposalService proposalService;

    @GetMapping("/preferences")
    public ResponseEntity<List<PreferenceDto>> getAllPreferences() {
        List<Preference> preferences = preferenceService.getAllPreferences();
        List<PreferenceDto> preferencesDto = preferences.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(preferencesDto);
    }

    @GetMapping("/users/{id}/preferences")
    public ResponseEntity<List<PreferenceDto>> getUserPreferences(@PathVariable Long id) {
        List<Preference> preferences = preferenceService.getUserPreferences(id);
        List<PreferenceDto> preferencesDto = preferences.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(preferencesDto);
    }

    @PostMapping("/users/{id}/preference/save")
    public ResponseEntity<PreferenceDto> savePreference(@RequestBody PreferenceDto preferenceDto,
                                                        @PathVariable Long id) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/users/{id}/preference/save")
                        .toUriString()
        );
        Preference preference = convertToEntity(preferenceDto, id);
        Preference preferenceSaved = preferenceService.savePreference(preference);
        return ResponseEntity.created(uri).body(convertToDto(preferenceSaved));
    }

    @DeleteMapping("/users/{id}/preferences/{propId}/delete")
    public ResponseEntity<PreferenceKey> deletePreference(@PathVariable Long id, @PathVariable Long propId) {
        PreferenceKey key = new PreferenceKey(id, propId);
        Preference preference = preferenceService.getPreference(key);
        preferenceService.deletePreference(key);
        return new ResponseEntity<>(key, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{studId}/preferences/{propId}/exists")
    public ResponseEntity<Boolean> preferenceExists(@PathVariable Long studId, @PathVariable Long propId) {
        PreferenceKey key = new PreferenceKey(studId, propId);
        boolean exists = preferenceService.existsPreference(key);
        return ResponseEntity.ok().body(exists);
    }

    private Preference convertToEntity(PreferenceDto preferenceDto, Long studId) {
        Preference preference = new Preference();
        Proposal proposal = proposalService.getProposal(preferenceDto.getProposalId());
        User student = userService.getUser(studId);
        preference.setProposal(proposal);
        preference.setStudent(student);
        preference.setRating(preferenceDto.getRating());
        return preference;
    }

    private PreferenceDto convertToDto(Preference preference) {
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setStudentId(preference.getStudent().getId());
        preferenceDto.setProposalId(preference.getProposal().getId());
        preferenceDto.setRating(preference.getRating());
        preferenceDto.setTitle(preference.getProposal().getTitle());
        preferenceDto.setDescription(preference.getProposal().getDescription());
        preferenceDto.setResources(preference.getProposal().getResources());
        preferenceDto.setProfUsername(preference.getProposal().getAuthor().getUsername());
        preferenceDto.setPlaces(preference.getProposal().getPlaces());
        return preferenceDto;
    }
}
