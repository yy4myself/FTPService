package com.example.myapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;

import com.example.ftp.manager.FtpServerManager;
import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class FtpService extends Service {

    private FtpServerManager mFtpServerManager;
    private String ftpConfigDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ftpConfig/";

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private static final String CHANNEL_ID = "NFCService";

    public FtpService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
        startFtpServer();
    }

    private void startFtpServer() {
        mFtpServerManager = FtpServerManager.getInstance();
        mFtpServerManager.configFtpServer(ftpConfigDir + "/users.properties", null);
        mFtpServerManager.startFtpService();
    }

    private void initNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, mainIntent, FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "主服务", NotificationManager.IMPORTANCE_HIGH);
            //显示logo
            channel.setShowBadge(true);
            //设置描述
            channel.setDescription("FTP服务端");
            //设置锁屏可见 VISIBILITY_PUBLIC=可见
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotificationManager.createNotificationChannel(channel);

            mNotification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("FTP服务")//标题
                    .setContentText("正在运行")//内容
                    .setWhen(System.currentTimeMillis())
                    //小图标一定需要设置,否则会报错(如果不设置它启动服务前台化不会报错,但是你会发现这个通知不会启动),如果是普通通知,不设置必然报错
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    //持续的通知，用户无法删除它，只能在代码中让通知消失
                    .setOngoing(true)
                    .build();
            //服务前台化只能使用startForeground()方法,不能使用 notificationManager.notify(1,notification); 这个只是启动通知使用的,使用这个方法你只需要等待几秒就会发现报错了
            startForeground(1, mNotification);
        } else {
            mNotification = new Notification.Builder(this)
                    .setContentTitle("FTP服务")
                    .setContentText("正在运行")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
            mNotificationManager.notify(1, mNotification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mFtpServerManager.stopFtpService();
        mNotificationManager.cancel(1);
        super.onDestroy();
    }
}
