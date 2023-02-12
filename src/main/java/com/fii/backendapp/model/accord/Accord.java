package com.fii.backendapp.model.accord;

import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accord {
    @EmbeddedId
    private AccordKey id = new AccordKey();
    private boolean isAccepted;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "stud_id")
    private User student;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "prof_id")
    private User professor;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_id", referencedColumnName = "id")
    private Proposal proposal;
}
