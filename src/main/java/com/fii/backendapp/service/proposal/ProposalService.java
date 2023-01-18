package com.fii.backendapp.service.proposal;

import com.fii.backendapp.domain.proposal.Proposal;

import java.util.Set;

public interface ProposalService {
    Proposal saveProposal(Proposal proposal);
    Proposal getProposal(Long id);
    Set<Proposal> getAllProposals();
    Set<Proposal> getProposalsOfUser(Long uid);
}
