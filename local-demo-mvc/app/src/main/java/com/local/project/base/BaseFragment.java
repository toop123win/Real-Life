package com.local.project.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;


/**
 * BaseFragment
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    public Activity mActivity;
    public Context context;
    public Gson gson = new Gson();
    public VB binding;
    private boolean isLoaded = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
           if (type!=null){
               Class<VB> clazz = (Class<VB>) type.getActualTypeArguments()[0];
               Method method = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
               binding = (VB) method.invoke(null, inflater, container, false);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isEventBusEnabled()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            onCreateFragment();
            isLoaded = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (gson != null) {
            gson = null;
        }
        if (binding != null) {
            binding = null;
        }
    }

    /**
     * 是否使用EventBus
     *
     * @return the boolean
     */
    protected boolean isEventBusEnabled() {
        return false;
    }

    public abstract void onCreateFragment();

}
