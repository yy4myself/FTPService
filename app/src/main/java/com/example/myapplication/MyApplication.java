package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.manager.FtpServerManager;

public class MyApplication extends Application {

    private static Context mContext;

    private FtpServerManager ftpServerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        ftpServerManager = FtpServerManager.getInstance();
    }

    public boolean initFtpService() {
        return ftpServerManager.initFTPServer();
    }

    public void startFtpService() {
        ftpServerManager.startFTPService();
    }

    public static Context getContext() {
        return mContext;
    }
}
