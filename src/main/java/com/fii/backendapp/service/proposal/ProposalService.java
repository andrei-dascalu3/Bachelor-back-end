package com.fii.backendapp.service.proposal;

import com.fii.backendapp.domain.proposal.Proposal;

import java.util.List;

public interface ProposalService {
    Proposal saveProposal(Proposal proposal);
    Proposal getProposal(Long id);
    List<Proposal> getAllProposals();
    List<Proposal> getProposalsOfUser(Long uid);
}
