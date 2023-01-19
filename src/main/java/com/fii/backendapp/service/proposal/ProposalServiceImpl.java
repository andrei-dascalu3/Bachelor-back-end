package com.fii.backendapp.service.proposal;

import com.fii.backendapp.dto.ProposalDto;
import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.model.user.User;
import com.fii.backendapp.repository.ProposalRepository;
import com.fii.backendapp.repository.UserRepository;
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
    public List<Proposal> getUserProposals(Long uid) {
        log.info("Fetching all proposals of user with id: {}", uid);
        return proposalRepo.findByAuthor_Id(uid);
    }

    @Override
    public void deleteProposal(Long id) {
        log.info("Deleting proposal with id: {}", id);
        proposalRepo.deleteById(id);
    }
}
