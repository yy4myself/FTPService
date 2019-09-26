package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ftp.FtpClient;
import com.example.myapplication.R;
import com.example.myapplication.fragment.LeftFragment;
import com.example.myapplication.service.FtpService;
import com.example.myapplication.util.CommonUtil;
import com.example.myapplication.util.PermissionUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout leftFrameLayout;
    private LeftFragment leftFragment;
    private Button buttonStartFtp, buttonStopFtp, buttonStartFtpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestPermissionsActivity.startPermissionActivityIfNeeded(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        leftFrameLayout = findViewById(R.id.left_frame_layout);
        leftFragment = LeftFragment.newInstance("s1", "s2");

        ViewGroup.LayoutParams layoutParam = leftFrameLayout.getLayoutParams();
        layoutParam.width = CommonUtil.getScreenWidth() * 3 / 5;
        leftFrameLayout.setLayoutParams(layoutParam);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.left_frame_layout, leftFragment);
        transaction.commit();

        buttonStartFtp = findViewById(R.id.button_startFtpServer);
        buttonStartFtp.setOnClickListener(this);
        buttonStopFtp = findViewById(R.id.button_stopFtpServer);
        buttonStopFtp.setOnClickListener(this);

        buttonStartFtpClient = findViewById(R.id.button_startFtpClient);
        buttonStartFtpClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_startFtpServer:
                if (PermissionUtil.hasReadExternalStoragePermissions(this)) {
                    boolean Jurisdiction = NotificationManagerCompat.from(this).areNotificationsEnabled();
                    if (Jurisdiction == true) {
                        Intent intent = new Intent(this, FtpService.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        } else {
                            startService(intent);
                        }
                    } else {
                        Toast.makeText(this, "情先开启通知", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请先授权", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_stopFtpServer:
                Intent intent = new Intent(this, FtpService.class);
                stopService(intent);
                break;
            case R.id.button_startFtpClient:
                thread.start();
                break;
        }
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            FtpClient.getInstance().initFTPSetting("192.168.1.74",21,"admin","admin");
        }
    });
}
