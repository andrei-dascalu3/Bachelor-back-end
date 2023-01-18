package com.fii.backendapp.repository;

import com.fii.backendapp.domain.proposal.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByProfId(Long profId);
}
