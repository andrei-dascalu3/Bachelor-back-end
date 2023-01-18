package com.fii.backendapp.repository;

import com.fii.backendapp.domain.proposal.Proposal;

import java.util.Set;

public interface ProposalRepository {
    Proposal findById(Long id);
    Set<Proposal> findByProfId(Long profId);
}
