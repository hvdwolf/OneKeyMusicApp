package com.syu.music;

import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;

import android.preference.PreferenceFragment;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.Toast;




public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    Context mContext;

    private BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter = new IntentFilter();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    public static final String TAG = "OneKeyMusicApp-SettingsFragment";



    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        //getPackages(mContext);
    }

    @Override
    public void onResume() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Intent intent = new Intent();
        String toastText = "";

        switch (key) {
            /* All the settings belonging to the SofiaServer */
            case MySettings.PREF_MEDIAPLAYER:
                intent.setAction(MySettings.ACTION_PREF_MEDIAPLAYER_CHANGED);
                intent.putExtra(MySettings.EXTRA_PREF_MEDIAPLAYER_ENABLED, sharedPreferences.getString(key,  ""));
                break;
            default:
                Log.d(TAG, "Invalid setting encountered");
                break;
       }

        Log.d(TAG, "updated key is " + key);
        Log.d(TAG, "updated string is " + sharedPreferences.getString(key, ""));
        toastText = "You updated key \"" + key + "\" to \"" + sharedPreferences.getString(key, "") + "\"";
        Toast mToast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
        mToast.show();


        if (intent.getAction() != null) {
        getActivity().sendBroadcast(intent);
        }


    }

}