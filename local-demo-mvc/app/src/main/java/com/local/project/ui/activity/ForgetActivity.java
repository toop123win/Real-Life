package com.local.project.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.local.project.base.BaseActivity;
import com.local.project.base.BaseApplication;
import com.local.project.dao.UserBean;
import com.local.project.dao.UserBeanDao;
import com.local.project.databinding.ActivityForgetBinding;
import com.local.project.dao.UserInfo;
import com.local.project.util.ToastUtil;


/**
 *
 */

public class ForgetActivity extends BaseActivity<ActivityForgetBinding> implements View.OnClickListener {

    private UserBeanDao dao;

    @Override
    public void initOnViewClick() {
        binding.btnRegister.setOnClickListener(this);

    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        dao = BaseApplication.getDaoSession().getUserBeanDao();
        binding.etUsername.setText(UserInfo.getInstance().getUsername());
        binding.etUsername.setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String password2 = binding.etPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showToast("填写用户名");
            Toast.makeText(ForgetActivity.this, "填写用户名", Toast.LENGTH_SHORT).show();
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

        UserBean userBean = dao.queryBuilder().where(UserBeanDao.Properties.Username.eq(username)).build().unique();
        if (userBean == null){
            ToastUtil.showToast("用户名不存在");
            return;
        }
        try {
            userBean.setPassword(password);
            dao.update(userBean);
            ToastUtil.showToast("修改成功");
            finish();
        } catch (Exception e) {
            ToastUtil.showToast("修改失败");
        }
    }
}
