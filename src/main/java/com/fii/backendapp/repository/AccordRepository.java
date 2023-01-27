package com.fii.backendapp.repository;

import com.fii.backendapp.model.accord.Accord;
import com.fii.backendapp.model.accord.AccordKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccordRepository extends JpaRepository<Accord, AccordKey> {
    List<Accord> findByIsAccepted(boolean isAccepted);
    List<Accord> findByStudent_Id(Long studId);
    List<Accord> findByStudent_IdAndIsAccepted(Long studId, boolean isAccepted);
    List<Accord> findByProfessor_Id(Long profId);
    Long countByProposal_Id(Long propId);
    boolean existsByStudent_IdAndIsAccepted(Long studId, boolean isAccepted);
}
