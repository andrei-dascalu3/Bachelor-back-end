package com.fii.backendapp.domain.accord;

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
public class Accord {
    @EmbeddedId
    private AccordKey id;
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
