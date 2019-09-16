package com.example.myapplication.util;

import android.content.Context;
import android.view.WindowManager;

import com.example.myapplication.MyApplication;

public class CommonUtil {

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) MyApplication.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
