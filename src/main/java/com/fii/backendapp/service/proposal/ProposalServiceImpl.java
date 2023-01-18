package com.fii.backendapp.service.proposal;

import com.fii.backendapp.domain.proposal.Proposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProposalServiceImpl implements ProposalService {

    @Override
    public Proposal saveProposal(Proposal proposal) {
        return null;
    }

    @Override
    public Proposal getProposal(Long id) {
        return null;
    }

    @Override
    public Set<Proposal> getAllProposals() {
        return null;
    }

    @Override
    public Set<Proposal> getProposalsOfUser(Long uid) {
        return null;
    }
}
