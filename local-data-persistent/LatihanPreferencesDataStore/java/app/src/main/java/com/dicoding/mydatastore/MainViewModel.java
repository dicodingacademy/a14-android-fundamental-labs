package com.dicoding.mydatastore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final SettingPreferences pref;

    public MainViewModel(SettingPreferences pref) {
        this.pref = pref;
    }

    public LiveData<Boolean> getThemeSettings() {
        return LiveDataReactiveStreams.fromPublisher(pref.getThemeSetting());
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        pref.saveThemeSetting(isDarkModeActive);
    }
}
