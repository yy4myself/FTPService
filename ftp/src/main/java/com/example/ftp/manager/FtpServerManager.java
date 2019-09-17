package com.example.ftp.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import java.io.File;

public class FtpServerManager {
    private static final String TAG = "FtpServerManager";
    //单例
    private static FtpServerManager instance;

    private FtpServerManager() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        mServerFactory = new FtpServerFactory();
        mListenerFactory = new ListenerFactory();
        mPropertiesUserManagerFactory = new PropertiesUserManagerFactory();
    }//构造函数私有化防止创建实例

    public static FtpServerManager getInstance() {//DCL的方式创建单例
        if (instance == null) {
            synchronized (FtpServerManager.class) {
                if (instance == null) {
                    instance = new FtpServerManager();
                }
            }
        }
        return instance;
    }

    //FTP Server 工厂
    private FtpServerFactory mServerFactory;
    //FTP Server对象
    private static FtpServer mFtpServer;
    //FTP Server 的端口工厂
    private ListenerFactory mListenerFactory;
    //默认端口号
    private int port = 2222;
    //FTP Server的用户设置工厂
    private PropertiesUserManagerFactory mPropertiesUserManagerFactory;

    /**
     * 配置FTP Server端，注意，需要先把已经开启的FTP Server关掉，否者配置不会生效
     *
     * @param fileName 配置文件的绝对路径，不能为空，为空会抛异常
     * @param port     需要配置的端口号，需要大于0，可以为空，当传入为空或者小于0时使用默认端口2222
     * @return 初始化的结果，true代表成功，false代表失败
     */
    public boolean configFtpServer(@NonNull String fileName, @NonNull Integer port) {
        if (TextUtils.isEmpty(fileName)) throw new RuntimeException("Ftp Config fileName is empty");
        if (port != null && port > 0) this.port = port;
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            mPropertiesUserManagerFactory.setFile(file);
            mServerFactory.setUserManager(mPropertiesUserManagerFactory.createUserManager());
            mListenerFactory.setPort(this.port);
            mServerFactory.addListener("default", mListenerFactory.createListener());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 开启FTP Server
     */
    public synchronized void startFtpService() {
        if (mFtpServer == null) mFtpServer = mServerFactory.createServer();
        if (mFtpServer.isStopped() == true) {
            try {
                mFtpServer.start();
            } catch (FtpException e) {
                Log.e(TAG, "startFtpService: start error ,reason = " + e.toString());
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭FTP Server
     */
    public synchronized void stopFtpService() {
        if (mFtpServer != null) {
            if (mFtpServer.isStopped() == false) mFtpServer.stop();
            mFtpServer = null;
        }
    }

    /**
     * 获取当前FTP Server的运行状态
     *
     * @return false代表FTP Server对象为空或者没有运行，true代表FTP Server正在运行
     */
    public boolean getRunningState() {
        return mFtpServer == null ? false : mFtpServer.isStopped();
    }
}
