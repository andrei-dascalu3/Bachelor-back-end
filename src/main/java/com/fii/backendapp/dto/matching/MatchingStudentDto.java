package com.fii.backendapp.dto.matching;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingStudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
}
