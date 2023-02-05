package com.fii.backendapp.service.accord;

import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.model.accord.AccordKey;
import com.fii.backendapp.repository.AccordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccordServiceImpl implements AccordService {

    private final AccordRepository accordRepo;

    @Override
    public List<Accord> getAllAccords() {
        // log.info("Fetching all accords");
        return accordRepo.findAll();
    }

    @Override
    public List<Accord> getAllAcceptedAccords() {
        // log.info("Fetching accepted accords");
        return accordRepo.findByIsAccepted(true);
    }

    @Override
    public List<Accord> getStudentAccords(Long studId) {
        // log.info("Fetching all accords of student with id: {}", studId);
        return accordRepo.findByStudent_Id(studId);
    }

    @Override
    public List<Accord> getProfessorAccords(Long profId) {
        // log.info("Fetching all accords of professor with id: {}", profId);
        return accordRepo.findByProfessor_Id(profId);
    }

    @Override
    public Accord getAccord(AccordKey id) {
        // log.info("Fetching accord with id (stud_id, prof_id): {}", id);
        return accordRepo.findById(id).get();
    }

    @Override
    public Accord saveAccord(Accord accord) {
        // log.info("Saving to the database a new accord with id: {}", accord.getId());
        return accordRepo.save(accord);
    }

    @Override
    public void deleteAccord(AccordKey id) {
        // log.info("Deleting accord with id (stud_id, prof_id): {}", id);
        accordRepo.deleteById(id);
    }

    @Override
    public boolean exists(AccordKey id) {
        // log.info("Checking existence of accord with id (stud_id, prof_id): {}", id);
        return accordRepo.existsById(id);
    }

    @Override
    public boolean existsByStudent_IdAndIsAccepted(Long studId, boolean isAccepted) {
        // log.info("Checking if student with id {} has accords accepted = ", studId, isAccepted);
        return accordRepo.existsByStudent_IdAndIsAccepted(studId, isAccepted);
    }
}
