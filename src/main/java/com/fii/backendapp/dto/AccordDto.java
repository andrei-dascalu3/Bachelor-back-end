package com.fii.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccordDto {
    Long profId;
    Long studId;
    Long propId;
    boolean isAccepted;
    String studUsername;
    String profUsername;
    String propTitle;
}
