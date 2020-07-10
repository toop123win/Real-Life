package com.local.project.ui.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TimePicker;

import com.local.project.R;
import com.local.project.base.BaseActivity;
import com.local.project.base.BaseApplication;
import com.local.project.dao.ScheduleBean;
import com.local.project.dao.ScheduleBeanDao;
import com.local.project.databinding.ActivityCreateScheduleBinding;
import com.local.project.dao.UserInfo;
import com.local.project.util.ToastUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;


//创建日程
public class ScheduleCreateActivity extends BaseActivity<ActivityCreateScheduleBinding> {

    private int year, month, day;
    private ScheduleBeanDao scheduleDao;
    private String[] type = new String[]{"创意", "打扫", "购物",
            "行程", "考试", "礼物",
            "缴费", "学习", "约会",
            "运动", "拍照", "做饭"};
    private int[] img = new int[]{R.drawable.ic_cy, R.drawable.ic_ds, R.drawable.ic_gw,
            R.drawable.ic_xc, R.drawable.ic_ks, R.drawable.ic_lw,
            R.drawable.ic_jf, R.drawable.ic_xx, R.drawable.ic_yh,
            R.drawable.ic_yd, R.drawable.ic_pz, R.drawable.ic_zf,};

    @Override
    public void initOnViewClick() {
        binding.createSchTime.setOnClickListener(v -> {
            new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                            String min = minute < 10 ? "0" + minute : "" + minute;
                            binding.createSchTime.setText(hour + ":" + min + ":00");
                        }
                    }, 12, 0, true).show();
        });
        binding.createSchType.setOnClickListener(v -> {
            new XPopup.Builder(context)
                    .asBottomList("请选择类别", type, img,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    binding.createSchType.setText(text);
                                }
                            })
                    .show();
        });
        binding.createSchSave.setOnClickListener(v -> {
            try {
                String content = binding.createSchContent.getText().toString();
                String time = binding.createSchTime.getText().toString();
                String type = binding.createSchType.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    ToastUtil.showToast("选择时间");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast("输入行程");
                    return;
                }
                if (TextUtils.isEmpty(type)) {
                    ToastUtil.showToast("选择类别");
                    return;
                }
                scheduleDao.insert(new ScheduleBean(
                        scheduleDao.queryBuilder().count(),
                        year, month, day,
                        UserInfo.getInstance().getUsername(),
                        time, type, content));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.createSchCancel.setOnClickListener(v -> finish());
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        year = getIntent().getIntExtra("year", 0);
        month = getIntent().getIntExtra("month", 0);
        day = getIntent().getIntExtra("day", 0);
        actionBar.setTitle(year + "年" + month + "月" + day + "日");
        scheduleDao = BaseApplication.getDaoSession().getScheduleBeanDao();
    }

}
