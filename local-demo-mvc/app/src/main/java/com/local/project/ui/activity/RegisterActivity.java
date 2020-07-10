package com.local.project.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.local.project.base.BaseApplication;
import com.local.project.dao.UserBean;
import com.local.project.dao.UserBeanDao;
import com.local.project.databinding.ActivityRegisterBinding;
import com.local.project.util.ToastUtil;
import com.local.project.base.BaseActivity;


/**
 * 注册
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements View.OnClickListener {

    private UserBeanDao dao;

    @Override
    public void initOnViewClick() {
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        dao = BaseApplication.getDaoSession().getUserBeanDao();
    }


    @Override
    public void onClick(View v) {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String password2 = binding.etPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showToast("填写用户名");
            Toast.makeText(RegisterActivity.this, "填写用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast("填写密码");
            return;
        }
        if (TextUtils.isEmpty(password2)) {
            ToastUtil.showToast("再次填写密码");

            return;
        }
        if (!password.equals(password2)) {
            ToastUtil.showToast("两次密码不同");
            return;
        }
        UserBean userBean = new UserBean(username, password);
        try {
            dao.insert(userBean);
            ToastUtil.showToast("注册成功");
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("注册失败");
        }
    }
}
