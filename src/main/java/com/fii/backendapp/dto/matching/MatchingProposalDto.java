package com.fii.backendapp.dto.matching;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingProposalDto {
    private Long id;
    private String title;
    private String description;
    private String resources;
    private String authorUsername;
}
