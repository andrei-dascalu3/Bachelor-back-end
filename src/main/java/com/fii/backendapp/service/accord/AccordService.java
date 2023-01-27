package com.fii.backendapp.service.accord;

import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.model.accord.AccordKey;

import java.util.List;

public interface AccordService {
    List<Accord> getAllAccords();
    List<Accord> getStudentAccords(Long studId);
    List<Accord> getProfessorAccords(Long profId);
    Accord getAccord(AccordKey id);
    Accord saveAccord(Accord accord);
    void deleteAccord(AccordKey id);
    boolean exists(AccordKey id);
    boolean existsByStudent_IdAndIsAccepted(Long studId, boolean isAccepted);
}
