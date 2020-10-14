package com.flavours5.common;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class AnalyticsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        Log.d("Application", "Firebase app initialized");
    }


}
