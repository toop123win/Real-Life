package com.local.project.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.local.project.base.BaseApplication;


/**
 * ToastUtil
 */

public class ToastUtil {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private static String qMsg = "";

    public static void showToast(String msg) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME || !qMsg.equals(msg)) {
            if (!TextUtils.isEmpty(msg)) {
                showShort(msg);
            }
            qMsg = msg;
        }
        lastClickTime = curClickTime;
    }

    private static void showShort(String message) {
        Toast mToast = Toast.makeText(BaseApplication.getInstance(), null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }
}
