package com.local.project.util;

import android.content.Context;
import android.content.res.Resources;

import com.local.project.dao.UserBean;
import com.local.project.dao.UserInfo;

public class AppUtils {

    /**
     * 登录保存用户信息
     *
     * @param userBean
     */
    public static void saveUserInfo(UserBean userBean) {
        UserInfo.getInstance().setUsername(userBean.getUsername());
        UserInfo.getInstance().setPassword(userBean.getPassword());
    }


    /**
     * 清除用户信息
     *
     * @param
     */
    public static void cleanUserInfo(Context context) {
        PrefUtils.putString(context,PrefUtils.LOGIN_USER_INFO,"");
        UserInfo.getInstance().setUsername("");
        UserInfo.getInstance().setPassword("");
    }



    /**
     * @param str 少
     * @param target 多
     * @return
     */
    public static float getSimilarityRatio(String str, String target) {
        int n = str.length();
        int temp = 0; // 记录相同字符+1
        for (int i = 0; i < n; i++) {
            String chi = str.substring(i, i + 1);
            if (target.contains(chi)) {
                temp++;
            }
        }
        return (float) temp / (float)str.length();
    }


    /**
     * dp转换px
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换dp
     * @param pxValue The value of px.
     * @return value of dp
     */
    public static int px2dp(final float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
