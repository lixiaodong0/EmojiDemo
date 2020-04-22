package com.lixd.emojidemo;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private Context context;
    private static App sApp;


    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        context = this;
    }


    public static App getInstance() {
        return sApp;
    }

    public Context getContext() {
        return context;
    }
}
