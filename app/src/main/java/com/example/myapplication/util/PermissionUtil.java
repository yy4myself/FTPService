package com.example.myapplication.util;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.content.ContextCompat;

public class PermissionUtil {
    public static final String INTERNET = Manifest.permission.INTERNET;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static boolean hasPhonePermissions(Context context) {
        return hasPermission(context, INTERNET);
    }

    public static boolean hasReadExternalStoragePermissions(Context context) {
        return hasPermission(context, READ_EXTERNAL_STORAGE);
    }

    public static boolean hasWriteExternalStoragePermissions(Context context) {
        return hasPermission(context, WRITE_EXTERNAL_STORAGE);
    }

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasAppOp(Context context, String appOp) {
        final AppOpsManager appOpsManager =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        final int mode = appOpsManager.checkOpNoThrow(appOp, Process.myUid(),
                context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }
}
