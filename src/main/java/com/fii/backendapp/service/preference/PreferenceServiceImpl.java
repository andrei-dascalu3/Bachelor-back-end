package com.fii.backendapp.service.preference;

import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.preference.PreferenceKey;
import com.fii.backendapp.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepo;

    @Override
    public List<Preference> getAllPreferences() {
        // log.info("Fetching all preferences");
        return preferenceRepo.findAll();
    }

    @Override
    public List<Preference> getUserPreferences(Long uid) {
        // log.info("Fetching all preferences of user with id: {}", uid);
        return preferenceRepo.findByStudent_IdOrderByRatingDesc(uid);
    }

    @Override
    public Preference getPreference(PreferenceKey key) {
        // log.info("Fetching preference with id (stud_id, prop_id): {}", key);
        return preferenceRepo.findById(key).get();
    }

    @Override
    public Preference savePreference(Preference preference) {
        // log.info("Saving to the database a new preference with id: {}", preference.getId());
        return preferenceRepo.save(preference);
    }

    @Override
    public void deletePreference(PreferenceKey id) {
        // log.info("Deleting preference with id (stud_id, prop_id): {}", id);
        preferenceRepo.deleteByStudent_IdAndProposal_Id(id.getStudentId(), id.getProposalId());
    }

    @Override
    public boolean exists(PreferenceKey id) {
        // log.info("Checking existence of preference with id (stud_id, prop_id): {}", id);
        return preferenceRepo.existsByStudent_IdAndProposal_Id(id.getStudentId(), id.getProposalId());
    }
}
