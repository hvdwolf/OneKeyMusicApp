package com.syu.music;


import android.content.Context;

import android.os.Bundle;

import android.util.AttributeSet;
import android.preference.PreferenceActivity;



public class SettingsActivity extends PreferenceActivity {
    public static final String TAG = "OneKeyMusicApp-SettingsActivity";

    Context mContext;
    AttributeSet attrs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getFragmentManager().findFragmentByTag("settings") == null) {
            getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment(), "settings")
                .commit();
        }

    }

}
