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
        return accordRepo.findAll();
    }

    @Override
    public List<Accord> getAllAcceptedAccords() {
        return accordRepo.findByIsAccepted(true);
    }

    @Override
    public List<Accord> getStudentAccords(Long studId) {
        return accordRepo.findByStudent_Id(studId);
    }

    @Override
    public List<Accord> getProfessorAccords(Long profId) {
        return accordRepo.findByProfessor_Id(profId);
    }

    @Override
    public Accord getAccord(AccordKey id) {
        var accord = accordRepo.findById(id);
        return accord.orElse(null);
    }

    @Override
    public Accord saveAccord(Accord accord) {
        return accordRepo.save(accord);
    }

    @Override
    public void deleteAccord(AccordKey id) {
        accordRepo.deleteById(id);
    }

    @Override
    public boolean exists(AccordKey id) {
        return accordRepo.existsById(id);
    }

    @Override
    public boolean existsByStudent_IdAndIsAccepted(Long studId, boolean isAccepted) {
        return accordRepo.existsByStudent_IdAndIsAccepted(studId, isAccepted);
    }
}
