package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.fragment.LeftFragment;
import com.example.myapplication.util.CommonUtil;
import com.example.myapplication.util.PermissionUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout leftFrameLayout;
    private LeftFragment leftFragment;
    private MyApplication myApplication;
    private Button buttonStartFtp;

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

        myApplication = (MyApplication) getApplication();

        buttonStartFtp = findViewById(R.id.button_startFtpService);
        buttonStartFtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_startFtpService:
                if (PermissionUtil.hasReadExternalStoragePermissions(this)) {
                    if (myApplication.initFtpService()) {
                        myApplication.startFtpService();
                    } else {
                        Toast.makeText(this, "请先授权", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请先授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
