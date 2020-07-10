package com.local.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.local.project.base.BaseApplication;
import com.local.project.R;
import com.local.project.dao.UserBean;
import com.local.project.dao.UserBeanDao;
import com.local.project.databinding.ActivityLoginBinding;
import com.local.project.util.AppUtils;
import com.local.project.util.PrefUtils;
import com.local.project.util.ToastUtil;
import com.local.project.base.BaseActivity;

import java.util.List;


/**
 * 登陆
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private UserBeanDao dao;

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        dao = BaseApplication.getDaoSession().getUserBeanDao();
    }


    @Override
    public void initOnViewClick() {
        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                ToastUtil.showToast("填写用户名");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast("填写密码");
                return;
            }
            UserBean bean = dao.queryBuilder()
                    .where(UserBeanDao.Properties.Username.eq(username)).build().unique();
            if (bean == null) {
                ToastUtil.showToast("请注册账号");
            } else {
                if (password.equals(bean.getPassword())) {
                    PrefUtils.putString(context, PrefUtils.LOGIN_USER_INFO, gson.toJson(bean));
                    AppUtils.saveUserInfo(bean);
                    finish();
                } else {
                    ToastUtil.showToast("密码错误");
                }
            }
        });
        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

}
