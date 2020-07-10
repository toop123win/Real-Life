package com.local.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.local.project.base.BaseActivity;
import com.local.project.databinding.ActivityUserInfoBinding;
import com.local.project.dao.UserInfo;
import com.local.project.util.AppUtils;

public class UserInfoActivity extends BaseActivity<ActivityUserInfoBinding> {



    @Override
    public void initOnViewClick() {
        binding.userInfoForget.setOnClickListener(v -> {
            startActivity(new Intent(context,ForgetActivity.class));
        });
        binding.userInfoExit.setOnClickListener(v -> {
            AppUtils.cleanUserInfo(context);
            finish();
        });
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.userInfoName.setText(UserInfo.getInstance().getUsername());

    }


}
