package com.example.myapplication.manager;

import android.os.Environment;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import java.io.File;

public class FtpServerManager {

    private static final String TAG = "FileUtil";
    private static FtpServerManager instance = new FtpServerManager();

    private FtpServerManager() {
    }//构造函数私有化防止创建实例

    public static FtpServerManager getInstance() {
        return instance;
    }

    private static FtpServer mFtpServer;
    private static int port = 2222;// 端口号
    private static String ftpConfigDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ftpConfig/";
    private static FtpServerFactory serverFactory;

    public boolean initFTPServer() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        String filename = ftpConfigDir + "users.properties";
        File file = new File(filename);
        if (file.exists()) {
            userManagerFactory.setFile(file);
            serverFactory.setUserManager(userManagerFactory.createUserManager());
            factory.setPort(port);
            serverFactory.addListener("default", factory.createListener());
            FtpServer server = serverFactory.createServer();
            mFtpServer = server;
            return true;
        } else {
            return false;
        }
    }

    public void startFTPService() {
        if (mFtpServer == null) {
            mFtpServer = serverFactory.createServer();
            try {
                mFtpServer.start();
            } catch (FtpException e) {
                e.printStackTrace();
            }
        } else {
            if (mFtpServer.isStopped() == true) {
                try {
                    mFtpServer.start();
                } catch (FtpException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void restartFTPService() {
        if (mFtpServer != null) {
            if (mFtpServer.isStopped() == false) {
                mFtpServer.resume();
            }
        }
    }

    public void stopFTPService() {
        if (mFtpServer != null) {
            if (mFtpServer.isStopped() == false) {
                mFtpServer.stop();
            }
            mFtpServer = null;
        }
    }
}
