package com.fii.backendapp.util;

import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.Role;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.service.accord.AccordService;
import com.fii.backendapp.service.preference.PreferenceService;
import com.fii.backendapp.service.proposal.ProposalService;
import com.fii.backendapp.service.user.UserService;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Populator {

    private UserService userService;
    private ProposalService proposalService;
    private PreferenceService preferenceService;
    private AccordService accordService;
    private Set<String> emails = new HashSet<>();
    private PopulatingConfiguration config;
    private Integer professorCount;

    public Populator(UserService userService, ProposalService proposalService, PreferenceService preferenceService,
                     AccordService accordService, PopulatingConfiguration config) {
        this.userService = userService;
        this.proposalService = proposalService;
        this.preferenceService = preferenceService;
        this.accordService = accordService;
        this.config = config;
    }

    public void populate() {
        populateUsers();
        populateProposals();
        populatePreferences();
    }

    private void populateUsers() {
        // adding roles
        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));

        // adding users
        professorCount = 0;
        Faker faker = new Faker();
        String firstName, lastName, description, email, password = "pass123456";
        boolean isProfessor;
        Integer adminCounter = 0;
        Integer userCounter = 0;
        User addedUser;
        while (userCounter < config.getUserCount()) {
            firstName = faker.name().firstName();
            lastName = faker.name().lastName();
            email = createUniqueEmail(firstName, lastName);
            email = email.replaceAll("[^a-zA-Z0-9.@]", "").toLowerCase();
            description = createDescription(email);
            isProfessor = decide(config.getProfessorProb());
            addedUser = new User(null, firstName, lastName, email, password, isProfessor, description, null,
                    new ArrayList<>(), new ArrayList<>());
            if (isProfessor) {
                professorCount++;
            }
            userService.saveUser(addedUser);
            userService.addRoleToUser(email, "ROLE_USER");
            if (adminCounter < config.getAdminCount()) {
                userService.addRoleToUser(email, "ROLE_ADMIN");
                adminCounter++;
            }
            userCounter++;
        }
    }

    private void populateProposals() {
        Integer studentCount = config.getUserCount() - professorCount;
        Double auxPlaceCount = Math.ceil(studentCount.doubleValue() / professorCount * config.getPropsToStudsRatio());
        Long placeCount = auxPlaceCount.longValue();
        Long places;
        List<User> professors = userService.getProfessors();
        for (var professor : professors) {
            Long availablePlaces = placeCount;
            Integer index = 1;
            while (availablePlaces > 0) {
                Proposal proposal = createProposal(professor, index);
                proposalService.saveProposal(proposal);
                places = proposal.getPlaces();
                if (places != null) {
                    availablePlaces -= places;
                } else {
                    availablePlaces -= 1;
                }
                index++;
            }
        }
    }

    void populatePreferences() {
        List<Proposal> proposals = proposalService.getAllProposals();
        List<User> students = userService.getStudents();
        List<Proposal> preferredProposals;
        Preference newPreference;
        for (var student : students) {
            Collections.shuffle(proposals);
            if (config.getPreferenceCount() < proposals.size()) {
                preferredProposals = proposals.subList(0, config.getPreferenceCount());
            } else {
                preferredProposals = proposals.subList(0, proposals.size());
            }
            for (var proposal : preferredProposals) {
                Long rating = new Long(randomInt(1, 101));
                newPreference = new Preference();
                newPreference.setStudent(student);
                newPreference.setProposal(proposal);
                newPreference.setRating(rating);
                preferenceService.savePreference(newPreference);
            }
        }
    }

    private String createUniqueEmail(String firstName, String lastName) {
        Random random = new Random();
        // to generate in case an additional number between [1, 1000) is needed
        int low = 0, high = 1000;
        StringBuilder builder = new StringBuilder();
        builder.append(firstName).append(".").append(lastName).append("@email.com");
        String email = builder.toString();
        while (emails.contains(email)) {
            builder = new StringBuilder();
            int randomNumber = random.nextInt(high - low) + low;
            builder.append(firstName).append(".").append(lastName).append(String.valueOf(randomNumber)).append(
                    "@email.com");
            email = builder.toString();
        }
        return email;
    }

    // passed argument is probability between [0, 100]
    private boolean decide(double prob) {
        if (prob < 0 || prob > 100) {
            throw new RuntimeException("Probability of being professor must be a real number between [0, 100].");
        }
        SplittableRandom random = new SplittableRandom();
        return random.nextInt(0, 101) <= prob;
    }

    private Integer randomInt(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private String createDescription(String email) {
        StringBuilder builder = new StringBuilder();
        builder.append("Description of user with username ").append(email).append(". Some other details.");
        return builder.toString();
    }

    private Proposal createProposal(User professor, Integer index) {
        Proposal proposal = new Proposal();
        Long places;
        String title, description, resources;
        StringBuilder builder = new StringBuilder();
        boolean isTopic = decide(config.getTopicProb());

        // deciding if it is topic, with multiple places, or project
        // setting title accordingly
        if (isTopic) {
            places = new Long(randomInt(1, config.getMaxTopicPlaces()));
            builder.append("Topic ").append(index).append(" of ").append(professor.getUsername());
        } else {
            places = null;
            builder.append("Project ").append(index).append(" of ").append(professor.getUsername());
        }
        title = builder.toString();
        // setting default description
        builder = new StringBuilder("Some generic description of ").append(title);
        description = builder.toString();
        // setting default resources
        resources = "link1\nlink2";
        //building proposal
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setResources(resources);
        proposal.setPlaces(places);
        proposal.setAuthor(professor);
        return proposal;
    }
}
