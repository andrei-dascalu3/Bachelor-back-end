package com.fii.backendapp.service.preference;

import com.fii.backendapp.model.preference.Preference;
import com.fii.backendapp.model.preference.PreferenceKey;

import java.util.List;

public interface PreferenceService {
    List<Preference> getAllPreferences();
    List<Preference> getUserPreferences(Long uid);
    Preference getPreference(PreferenceKey id);
    Preference savePreference(Preference preference);
    void deletePreference(PreferenceKey id);
    boolean exists(PreferenceKey id);
}
