package com.fii.backendapp.domain.proposal;

import com.fii.backendapp.domain.user.User;
import com.fii.backendapp.domain.accord.Accord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private Long places;
    @ManyToOne
    @JoinColumn(name = "prof_id", nullable = false)
    private User author;
    @OneToOne(mappedBy = "proposal")
    private Accord accord;
    @OneToMany(mappedBy = "proposal")
    private Set<Resource> resources;
}
