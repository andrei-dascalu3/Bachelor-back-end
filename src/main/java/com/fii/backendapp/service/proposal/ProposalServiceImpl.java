package com.fii.backendapp.service.proposal;

import com.fii.backendapp.model.proposal.Proposal;
import com.fii.backendapp.repository.AccordRepository;
import com.fii.backendapp.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepo;
    private final AccordRepository accordRepo;

    @Override
    public Proposal saveProposal(Proposal proposal) {
        return proposalRepo.save(proposal);
    }

    @Override
    public Proposal getProposal(Long id) {
        var proposal = proposalRepo.findById(id);
        return proposal.orElse(null);
    }

    @Override
    public List<Proposal> getAllProposals() {
        return proposalRepo.findAll();
    }

    @Override
    public List<Proposal> getUserProposals(Long uid) {
        return proposalRepo.findByAuthor_Id(uid);
    }

    @Override
    public List<Proposal> getAvailableUserProposals(Long id) {
        List<Proposal> proposals = proposalRepo.findByAuthor_Id(id);
        List<Proposal> result = new ArrayList<>();
        long totalPlaces;
        long busyPlaces;

        for (var proposal : proposals) {
            totalPlaces = proposal.getPlaces() != null ? proposal.getPlaces() : 1;
            busyPlaces = accordRepo.countByProposal_Id(proposal.getId());
            if(totalPlaces > busyPlaces) {
                result.add(proposal);
            }
        }
        return result;
    }

    @Override
    public void deleteProposal(Long id) {
        proposalRepo.deleteById(id);
    }
}
