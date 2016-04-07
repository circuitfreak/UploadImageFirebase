package com.jorickcaberio.uploadimagefirebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by jorickcaberio on 4/8/16.
 */
public class UploadImageApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
