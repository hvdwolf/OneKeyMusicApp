package com.syu.music;

import android.app.Activity;
//import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.widget.Toast;

public class OneKeyMusic extends Activity {
    public static final String TAG = "OneKeyMusicApp";
    public static Context mContext;
    private static PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String musicplayer_entry = sharedPref.getString(MySettings.PREF_MEDIAPLAYER, "");

        Log.d(TAG, "Started OneKeyMusicApp; in OnCreate void");
        //Toast mToast = Toast.makeText(OneKeyMusic.this, "In On Create", Toast.LENGTH_LONG);
        //mToast.show();
        startPlayer(OneKeyMusic.this, musicplayer_entry);
        finish();
    }


    private void startPlayer(Context context, String mediaplayerString) {
        if (mediaplayerString != "") {
            startActivityByPackageName(context, mediaplayerString);
            Log.d(TAG, "Start Package:" + mediaplayerString);
        }
    }

    public void startActivityByPackageName(Context context, String packageName) {
        PackageManager pManager = context.getPackageManager();
        Intent intent = pManager.getLaunchIntentForPackage(packageName);
        Log.d(TAG, "startActivityByPackageName: " + packageName);
        if (intent != null) {
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } else {
            Toast mToast = Toast.makeText(context, "No application defined yet", Toast.LENGTH_LONG);
            mToast.show();
            startActivityByIntentName(context, "com.syu.music/.SettingsActivity");
        }
    }

    public void startActivityByIntentName(Context context, String component) {
        Intent sIntent = new Intent(Intent.ACTION_MAIN);
        sIntent.setComponent(ComponentName.unflattenFromString(component));
        sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sIntent);
    }

}
