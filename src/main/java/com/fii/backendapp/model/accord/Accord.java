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
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "stud_id")
    private User student;
    @ManyToOne
    @MapsId("professorId")
    @JoinColumn(name = "prof_id")
    private User professor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_id", referencedColumnName = "id")
    private Proposal proposal;
}
