package com.cerkerli.slideview;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import demo.com.library.utils.Library;

public class MyApp extends Application {
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Library.init(getApplicationContext());

        instance = this;


        LeakCanary.install(this);
    }

    public static MyApp getInstance() {
        return instance;
    }

}


