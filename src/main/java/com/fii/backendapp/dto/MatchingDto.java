package com.fii.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingDto {
    private Long studentId;
    private Long proposalId;
    private Double cost;
}
