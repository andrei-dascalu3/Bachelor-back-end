package com.fii.backendapp.repository;

import com.fii.backendapp.model.proposal.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByAuthor_Id(Long uid);
    void deleteById(Long id);
}
