package com.local.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


import com.local.project.R;
import com.local.project.base.BaseActivity;
import com.local.project.base.BaseApplication;
import com.local.project.dao.DiaryBean;
import com.local.project.dao.DiaryBeanDao;
import com.local.project.dao.UserInfo;
import com.local.project.databinding.ActivityDiaryListBinding;
import com.local.project.widget.decoration.LinearLayoutDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.jaaksi.pickerview.picker.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryListActivity extends BaseActivity<ActivityDiaryListBinding> {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    private DiaryBeanDao diaryDao;
    private List<DiaryBean> list = new ArrayList<>();
    private Calendar calendar;
    private int year, month;
    private CommonAdapter<DiaryBean> adapter;

    @Override
    public void initOnViewClick() {
        binding.diaryListTime.setOnClickListener(v -> {
            int type = TimePicker.TYPE_YEAR | TimePicker.TYPE_MONTH;
            new TimePicker.Builder(mActivity, type, new TimePicker.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(TimePicker picker, Date date) {
                    String time = format.format(date);
                    String[] strings = time.split("-");
                    year = Integer.parseInt(strings[0]);
                    month = Integer.parseInt(strings[1]);
                    binding.diaryListTime.setText(format.format(date));
                    refreshData();
                }
            })
                    .setSelectedDate(System.currentTimeMillis())
                    .create().show();


        });
        binding.diaryListAdd.setOnClickListener(v -> {
            startActivity(new Intent(context, DiaryCreateActivity.class));
        });
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        diaryDao = BaseApplication.getDaoSession().getDiaryBeanDao();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        binding.diaryListTime.setText(year + "-" + month);
        initList();
    }

    private void initList() {
        binding.diaryListRecycler.addItemDecoration(new LinearLayoutDividerItemDecoration.Builder()
                .setDividerHeight(1).build());
        adapter = new CommonAdapter<DiaryBean>(context, R.layout.item_diary, list) {
            @Override
            protected void convert(ViewHolder holder, DiaryBean diaryBean, int position) {
                holder.setText(R.id.itemDiaryDay, diaryBean.getDay() + "号");
                holder.setImageResource(R.id.itemDiaryType, diaryBean.getType());
                holder.setText(R.id.itemDiaryTitle, diaryBean.getTitle());
                holder.setOnClickListener(R.id.itemDiaryLayout,v -> {
                    startActivity(new Intent(context,DiaryDetailActivity.class)
                    .putExtra("id",diaryBean.getId()));
                });
            }
        };
        binding.diaryListRecycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData(){
        list.clear();
        list.addAll(diaryDao.queryBuilder()
                .where(DiaryBeanDao.Properties.Year.eq(year),
                        DiaryBeanDao.Properties.Month.eq(month),
                        DiaryBeanDao.Properties.Username.eq(UserInfo.getInstance().getUsername()))
                .build().list());
        adapter.notifyDataSetChanged();
    }
}
