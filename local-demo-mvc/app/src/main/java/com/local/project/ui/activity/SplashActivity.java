package com.local.project.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ClickUtils;
import com.local.project.base.BaseApplication;
import com.local.project.base.BaseActivity;
import com.local.project.dao.UserBean;
import com.local.project.dao.UserBeanDao;
import com.local.project.databinding.ActivitySplashBinding;
import com.local.project.util.AppUtils;
import com.local.project.util.PrefUtils;
import com.local.project.util.ToastUtil;
import com.gyf.immersionbar.BarHide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private CountDownTimer timeCount;

    @Override
    public void initOnViewClick() {
        binding.splashTime.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                timeCount.cancel();
                intentActivity();
            }
        });
    }


    @Override
    protected void initImmersionBar() {
        mImmersionBar.autoStatusBarDarkModeEnable(true, 0.2f).transparentStatusBar().init();
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        XXPermissions.with(mActivity)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE) //不指定权限则自动获取清单中的危险权限
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (!isAll) {
                            ToastUtil.showToast("你已拒相关权限");
                        }
                        timeCount = new CountDownTimer(2200, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                binding.splashTime.setText("跳过 " + millisUntilFinished / 1000);
                            }

                            @Override
                            public void onFinish() {
                                intentActivity();
                            }
                        };
                        timeCount.start();
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }


    private void intentActivity() {
        String info = PrefUtils.getString(context,PrefUtils.LOGIN_USER_INFO,"");
        if (!TextUtils.isEmpty(info)){
            UserBean userBean = gson.fromJson(info,UserBean.class);
            AppUtils.saveUserInfo(userBean);
        }
        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }
}
