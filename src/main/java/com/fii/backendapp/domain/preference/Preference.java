package com.fii.backendapp.domain.preference;

import com.fii.backendapp.domain.proposal.Proposal;
import com.fii.backendapp.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preference {
    @EmbeddedId
    private PreferenceKey id;
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "stud_id")
    private User student;
    @ManyToOne
    @MapsId("proposalId")
    @JoinColumn(name = "prop_id")
    private Proposal proposal;
    private Long rating;
}
