package com.example.kh.myapplication.Acitivity;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by kh on 7/3/2017.
 */

public class MyActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();
    }
}
