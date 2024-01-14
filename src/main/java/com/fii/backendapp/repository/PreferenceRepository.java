package com.fii.backendapp.repository;

import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.preference.PreferenceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, PreferenceKey> {
    List<Preference> findByStudent_Id(Long studId);
    List<Preference> findByStudent_IdOrderByRatingDesc(Long studId);
    List<Preference> findByProposal_Id(Long propId);
    void deleteById(PreferenceKey id);
    void deleteByStudent_IdAndProposal_Id(Long studentId, Long proposalId);
    boolean existsByStudent_IdAndProposal_Id(Long studentId, Long proposalId);
}
