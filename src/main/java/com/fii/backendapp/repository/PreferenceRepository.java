package com.fii.backendapp.repository;

import com.fii.backendapp.domain.preference.Preference;

import java.util.Set;

public interface PreferenceRepository {
    Set<Preference> findByStudId(Long studId);
    Set<Preference> findByProfId(Long profId);
}
