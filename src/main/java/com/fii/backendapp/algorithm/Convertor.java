package com.fii.backendapp.algorithm;

import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.preference.PreferenceService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class Convertor {
    private final AccordService accordService;
    private final UserService userService;
    private final ProposalService proposalService;
    private final PreferenceService preferenceService;
    private List<Long> studIds;
    private List<Long> propIds;
    private Map<Long, Long> accordIds = new HashMap<>();
    private Map<Long, Integer> studIndices = new HashMap<>();
    private Map<Long, List<Integer>> propIndices = new HashMap<>();
    private Map<Edge, Double> c;

    public void initialize() {
        c = new HashMap<>();
        createIdAndIndexCollections();
        // converting preferences into map entries
        List<Preference> prefs;
        List<Integer> indices;
        Long prevRating;
        Long propId;
        double cost;

        for (var studId : studIds) {
            Integer i = studIndices.get(studId);
            prefs = preferenceService.getUserPreferences(studId);
            cost = 0.0;
            prevRating = 0L;

            for (var pref : prefs) {
                propId = pref.getProposal().getId();
                if (prevRating == 0 || prevRating > pref.getRating()) {
                    cost += 1.0;
                }
                indices = propIndices.get(propId);
                if(indices != null) {
                    for (var j : indices) {
                        c.put(new Edge(i, j), cost);
                    }
                    prevRating = pref.getRating();
                }
            }
        }
    }

    private void createIdAndIndexCollections() {
        // fetching ids of assigned proposals and students
        List<Accord> accords = accordService.getAllAcceptedAccords();
        List<Long> assignedStudIds = new ArrayList<>();
        List<Long> assignedPropIds = new ArrayList<>();
        Long assignedStudId;
        Long assignedPropId;

        for (var accord : accords) {
            assignedStudId = accord.getStudent().getId();
            assignedPropId = accord.getProposal().getId();
            accordIds.put(assignedStudId, assignedPropId);
            assignedStudIds.add(assignedStudId);
            assignedPropIds.add(assignedPropId);
        }

        // fetching student ids
        studIds = userService.getStudents().stream().map(User::getId).collect(Collectors.toList());
        // fetching proposals
        List<Proposal> proposals = proposalService.getAllProposals();
        // creating list of proposal ids
        propIds = new ArrayList<>();
        // adding duplicates if proposal (topic) has multiple places
        for (var prop : proposals) {
            if (prop.getPlaces() == null) {
                propIds.add(prop.getId());
            } else {
                for (int i = 0; i < prop.getPlaces(); ++i) {
                    propIds.add(prop.getId());
                }
            }
        }
        // filtering out assigned students
        for (var id : assignedStudIds) {
            studIds.remove(id);
        }
        // filtering out assigned proposals
        for (var id : assignedPropIds) {
            propIds.remove(id);
        }
        // mapping student ids to their indices from studIds
        for (int i = 0; i < studIds.size(); ++i) {
            studIndices.put(studIds.get(i), i);
        }
        // mapping proposal ids to list of indices from propIds
        for (int i = 0; i < propIds.size(); ++i) {
            Long propId = propIds.get(i);
            if (!propIndices.containsKey(propId)) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                propIndices.put(propId, list);
            } else {
                propIndices.get(propId).add(i);
            }
        }
    }
}
