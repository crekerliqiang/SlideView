package com.cerkerli.slideview;

import android.app.Application;

import demo.com.library.Library;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Library.init(getApplicationContext());
    }
}
