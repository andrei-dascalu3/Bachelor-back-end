package com.fii.backendapp.dto;

import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isProfessor;
    private String description;
    private String imagePath;
    private Collection<Role> roles;
    private Collection<Proposal> proposals;
}
