package com.fii.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDto {
    private Long id;
    private String title;
    private String description;
    private Long places;
    private Long profId;
}
