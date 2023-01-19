package com.fii.backendapp.service.proposal;

import com.fii.backendapp.dto.ProposalDto;
import com.fii.backendapp.model.proposal.Proposal;

import java.util.List;

public interface ProposalService {
    Proposal saveProposal(Proposal proposalDto);
    Proposal getProposal(Long id);
    List<Proposal> getAllProposals();
    List<Proposal> getUserProposals(Long uid);
    void deleteProposal(Long id);
}
