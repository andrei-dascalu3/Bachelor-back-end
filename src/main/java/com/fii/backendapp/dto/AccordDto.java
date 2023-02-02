package com.fii.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccordDto {
    private Long profId;
    private Long studId;
    private Long propId;
    private boolean isAccepted;
    private String studUsername;
    private String profUsername;
    private String propTitle;
}
