package com.fii.backendapp.service.proposal;

import com.fii.backendapp.domain.proposal.Proposal;
import com.fii.backendapp.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepo;


    @Override
    public Proposal saveProposal(Proposal proposal) {
        log.info("Saving to the database a new proposal of user with id: {}", proposal.getAuthor().getId());
        log.info(proposal.toString());
        return proposalRepo.save(proposal);
    }

    @Override
    public Proposal getProposal(Long id) {
        log.info("Fetching proposal with id: {}", id);
        return proposalRepo.findById(id).get();
    }

    @Override
    public List<Proposal> getAllProposals() {
        log.info("Fetching all proposals");
        return proposalRepo.findAll();
    }

    @Override
    public List<Proposal> getProposalsOfUser(Long uid) {
        log.info("Fetching all proposals of user with id: {}", uid);
        return proposalRepo.findByProfId(uid);
    }
}
