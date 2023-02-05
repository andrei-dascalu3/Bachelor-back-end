package com.fii.backendapp.dto.matching;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingDto {
    private MatchingStudentDto student;
    private MatchingProposalDto proposal;
    private Double cost;
}
