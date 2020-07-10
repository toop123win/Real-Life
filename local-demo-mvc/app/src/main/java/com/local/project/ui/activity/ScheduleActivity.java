package com.local.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.local.project.base.BaseActivity;
import com.local.project.base.BaseApplication;
import com.local.project.dao.ScheduleBean;
import com.local.project.dao.ScheduleBeanDao;
import com.local.project.dao.UserInfo;
import com.local.project.ui.adapter.ArticleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//记日程
public class ScheduleActivity extends BaseActivity<com.local.project.databinding.ActivityQuestionBinding> implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnYearChangeListener {

    private List<ScheduleBean> list = new ArrayList<>();
    private List<ScheduleBean> select = new ArrayList<>();
    private ScheduleBeanDao scheduleDao;
    private int year, month, day;
    private ArticleAdapter adapter;

    @Override
    public void initOnViewClick() {
        binding.tvMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.calendarLayout.isExpand()) {
                    binding.calendarLayout.expand();
                    return;
                }
                binding.calendarView.showYearSelectLayout(year);
                binding.tvLunar.setVisibility(View.GONE);
                binding.tvYear.setVisibility(View.GONE);
                binding.tvMonthDay.setText(String.valueOf(year));
            }
        });
        binding.flCurrent.setOnClickListener(v -> {
            binding.calendarView.scrollToCurrent();
        });
        binding.calendarView.setOnCalendarSelectListener(this);
        binding.calendarView.setOnYearChangeListener(this);
        binding.calendarView.setOnCalendarLongClickListener(this, false);
        binding.scheduleAdd.setOnClickListener(v -> {
            startActivity(new Intent(context, ScheduleCreateActivity.class)
                    .putExtra("year", year)
                    .putExtra("month", month)
                    .putExtra("day", day));
        });
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        scheduleDao = BaseApplication.getDaoSession().getScheduleBeanDao();
        binding.tvYear.setText(String.valueOf(binding.calendarView.getCurYear()));
        year = binding.calendarView.getCurYear();
        month = binding.calendarView.getCurMonth();
        day = binding.calendarView.getCurDay();
        binding.tvMonthDay.setText(month + "月" + day + "日");
        binding.tvLunar.setText("今日");
        binding.tvCurrentDay.setText(String.valueOf(binding.calendarView.getCurDay()));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        list.clear();
        list.addAll(scheduleDao.queryBuilder()
                .where(ScheduleBeanDao.Properties.Username.eq(UserInfo.getInstance().getUsername()))
                .build().list());
        select.clear();
        select.addAll(scheduleDao.queryBuilder()
                .where(ScheduleBeanDao.Properties.Username.eq(UserInfo.getInstance().getUsername()),
                        ScheduleBeanDao.Properties.Year.eq(year),
                        ScheduleBeanDao.Properties.Month.eq(month),
                        ScheduleBeanDao.Properties.Day.eq(day))
                .build().list());
        Map<String, Calendar> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            ScheduleBean bean = list.get(i);
            map.put(getSchemeCalendar(bean.getYear(), bean.getMonth(), bean.getDay(), bean.getContent()).toString(),
                    getSchemeCalendar(bean.getYear(), bean.getMonth(), bean.getDay(), bean.getContent()));
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        binding.calendarView.clearSchemeDate();
        binding.calendarView.setSchemeDate(map);
        adapter.setData(select);
    }


    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        year = calendar.getYear();
        month = calendar.getMonth();
        day = calendar.getDay();
        binding.tvLunar.setVisibility(View.VISIBLE);
        binding.tvYear.setVisibility(View.VISIBLE);
        binding.tvMonthDay.setText(month + "月" + day + "日");
        binding.tvYear.setText(String.valueOf(year));
        binding.tvLunar.setText(calendar.getLunar());
        initData();
    }

    @Override
    public void onYearChange(int year) {
        binding.tvMonthDay.setText(String.valueOf(year));
    }


    private Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(0xFFF7B200);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
}
