package com.fii.backendapp.domain.accord;

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
public class AccordKey implements Serializable {
    @Column(name = "stud_id")
    private Long studentId;
    @Column(name = "prof_id")
    private Long professorId;
}