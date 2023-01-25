package com.fii.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDto {
    private Long studentId;
    private Long proposalId;
    private Long rating;
    private String title;
    private String description;
    private String resources;
    private String profUsername;
    private Long places;
}
