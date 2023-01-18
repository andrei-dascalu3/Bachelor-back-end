package com.fii.backendapp.domain.preference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceKey implements Serializable {
    @Column(name = "stud_id")
    private Long studentId;
    @Column(name = "prop_id")
    private Long proposalId;
}
