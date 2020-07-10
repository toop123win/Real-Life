package com.local.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.local.project.databinding.ActivityMainBinding;
import com.local.project.dao.UserInfo;
import com.local.project.base.BaseActivity;

import com.local.project.util.ToastUtil;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private long exitTime = 0;

    @Override
    public void initOnViewClick() {
        binding.mainCeshi.setOnClickListener(v -> {
            if (TextUtils.isEmpty(UserInfo.getInstance().getUsername())){
               ToastUtil.showToast("请先登录账号");
            }else {
                startActivity(new Intent(context, ScheduleActivity.class));
            }
        });
        binding.mainRank.setOnClickListener(v -> {
            if (TextUtils.isEmpty(UserInfo.getInstance().getUsername())){
                ToastUtil.showToast("请先登录账号");
            }else {
                startActivity(new Intent(context, DiaryListActivity.class));
            }

        });
        binding.mainName.setOnClickListener(v -> {
            if (TextUtils.isEmpty(UserInfo.getInstance().getUsername())){
                startActivity(new Intent(context, LoginActivity.class));
            }else {
                startActivity(new Intent(context, UserInfoActivity.class));
            }
        });

    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        if (actionBar != null) {
            actionBar.setTitle("RealLife");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(UserInfo.getInstance().getUsername())){
            binding.mainName.setText("请登录");
        }else {
            binding.mainName.setText(UserInfo.getInstance().getUsername());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
