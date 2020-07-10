package com.local.project.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.local.project.R;
import com.local.project.base.BaseActivity;
import com.local.project.base.BaseApplication;
import com.local.project.dao.DiaryBean;
import com.local.project.dao.DiaryBeanDao;
import com.local.project.dao.UserInfo;
import com.local.project.databinding.ActivityDiaryCreateBinding;
import com.local.project.databinding.ActivityDiaryDetailBinding;
import com.local.project.util.ToastUtil;
import com.local.project.util.VoiceManager;
import com.local.project.widget.ExpressionPopup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.jaaksi.pickerview.picker.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryDetailActivity extends BaseActivity<ActivityDiaryDetailBinding> {

    private VoiceManager manager;

    @Override
    public void initOnViewClick() {

    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        manager = VoiceManager.getInstance(context);
        DiaryBeanDao diaryDao = BaseApplication.getDaoSession().getDiaryBeanDao();
        long id = getIntent().getLongExtra("id",0);
        DiaryBean diaryBean = diaryDao.queryBuilder()
                .where(DiaryBeanDao.Properties.Id.eq(id))
                .build().unique();
        if (diaryBean !=null){
            binding.diaryCreateTime.setText(diaryBean.getYear()+"-"+diaryBean.getMonth()+"-"+diaryBean.getDay());
            binding.diaryCreateTitle.setText(diaryBean.getTitle());
            binding.diaryCreateContent.setText(diaryBean.getContent());
            binding.diaryCreateType.setImageResource(diaryBean.getType());
            manager.startPlay(diaryBean.getMusic());
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.stopPlay();
    }


}
