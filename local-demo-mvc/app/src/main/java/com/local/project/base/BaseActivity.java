package com.local.project.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.local.project.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;


/**
 * BaseActivity
 */

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    public Context context;
    public Activity mActivity;
    protected ImmersionBar mImmersionBar;
    public VB binding;
    public Gson gson;
    public ActionBar actionBar;

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            if (type != null) {
                Class<VB> clazz = (Class<VB>) type.getActualTypeArguments()[0];
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                binding = (VB) method.invoke(null, getLayoutInflater());
                setContentView(binding.getRoot());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        context = this;
        mActivity = this;
        actionBar = getSupportActionBar();
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            initImmersionBar();
        }
        if (isEventBusEnabled()) {
            EventBus.getDefault().register(this);
        }
        gson = new Gson();
        onCreateView(savedInstanceState);
        initOnViewClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initImmersionBar() {
        mImmersionBar.barColor(R.color.colorPrimary)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .init();
    }

    public abstract void initOnViewClick();

    public abstract void onCreateView(Bundle savedInstanceState);

    /**
     * 是否可以使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否使用EventBus
     *
     * @return the boolean
     */
    protected boolean isEventBusEnabled() {
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (gson != null) {
            gson = null;
        }
        if (actionBar != null) {
            actionBar = null;
        }
        if (binding != null) {
            binding = null;
        }
    }

}
