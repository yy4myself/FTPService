package com.example.myapplication.activity;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RequestPermissionsActivity extends RequestPermissionBaseActivity {
    public static final String BROADCAST_PERMISSIONS_GRANTED = "broadcastPermissionsGranted";

    private static String[] sRequiredPermissions;

    @Override
    protected String[] getPermissions() {
        return getPermissions(getPackageManager());
    }

    /**
     * Method to check if the required permissions are given.
     */
    public static boolean hasRequiredPermissions(Context context) {
        return hasPermissions(context, getPermissions(context.getPackageManager()));
    }

    public static boolean startPermissionActivityIfNeeded(Activity activity) {
        return startPermissionActivity(activity,
                getPermissions(activity.getPackageManager()),
                RequestPermissionsActivity.class);
    }

    private static String[] getPermissions(PackageManager packageManager) {
        if (sRequiredPermissions == null) {
            final List<String> permissions = new ArrayList<>();
            // 读写存储卡
            permissions.add(permission.READ_EXTERNAL_STORAGE);
            permissions.add(permission.WRITE_EXTERNAL_STORAGE);

            //检查是否有该功能模块，如果有该功能模块才进行权限申请
            // Phone group
            // These are only used in a few places such as QuickContactActivity and
            // ImportExportDialogFragment.  We work around missing this permission when
            // telephony is not available on the device (i.e. on tablets).
//            if (packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
//                permissions.add(permission.CALL_PHONE);
//                permissions.add(permission.READ_CALL_LOG);
//                permissions.add(permission.READ_PHONE_STATE);
//            }

            //查看手机所有功能模块的代码
//            PackageManager pm = this.getPackageManager();
//            FeatureInfo[] features = pm.getSystemAvailableFeatures(); //得到所有支援的硬件种类
//            for (FeatureInfo feature : features) Log.v(TAG, feature.name);
            sRequiredPermissions = permissions.toArray(new String[0]);
        }
        return sRequiredPermissions;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        if (permissions != null && permissions.length > 0
                && isAllGranted(permissions, grantResults)) {
            mPreviousActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (mIsCallerSelf) {
                startActivityForResult(mPreviousActivityIntent, 0);
            } else {
                startActivity(mPreviousActivityIntent);
            }
            finish();
            overridePendingTransition(0, 0);

            LocalBroadcastManager.getInstance(this).sendBroadcast(
                    new Intent(BROADCAST_PERMISSIONS_GRANTED));
        } else {
            Toast.makeText(this, R.string.missing_required_permission, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
