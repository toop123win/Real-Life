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
import com.local.project.dao.UserBean;
import com.local.project.dao.UserInfo;
import com.local.project.databinding.ActivityDiaryCreateBinding;
import com.local.project.databinding.ActivityDiaryListBinding;
import com.local.project.util.AppUtils;
import com.local.project.util.ToastUtil;
import com.local.project.util.VoiceManager;
import com.local.project.widget.ExpressionPopup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DiaryCreateActivity extends BaseActivity<ActivityDiaryCreateBinding> {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private int[] music = new int[]{R.raw.littlestory, R.raw.paladin, R.raw.townofwindmill};
    private String[] musicStr = new String[]{"littlestory", "paladin", "townofwindmill"};
    private int[] musicIc = new int[]{R.drawable.ic_music, R.drawable.ic_music, R.drawable.ic_music};
    private VoiceManager manager;
    private DiaryBean diaryBean;
    private DiaryBeanDao diaryDao;
    private boolean isFirst = true;

    @Override
    public void initOnViewClick() {
        binding.diaryCreateTime.setOnClickListener(v -> {
            showTime();
        });
        binding.diaryCreateMusic.setOnClickListener(v -> {
            new XPopup.Builder(context)
                    .asCenterList("请选择音乐", musicStr, musicIc,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    manager.startPlay(music[position]);
                                    diaryBean.setMusic(music[position]);
                                    binding.diaryCreateMusic.setText(text);
                                }
                            })
                    .show();
        });
        binding.diaryCreateType.setOnClickListener(v -> {
            showExpression();
        });
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        manager = VoiceManager.getInstance(context);
        diaryDao = BaseApplication.getDaoSession().getDiaryBeanDao();
        diaryBean = new DiaryBean();
        showExpression();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        String title = binding.diaryCreateTitle.getText().toString();
        String content = binding.diaryCreateContent.getText().toString();
        diaryBean.setUsername(UserInfo.getInstance().getUsername());
        diaryBean.setId(diaryDao.queryBuilder().count());
        diaryBean.setTitle(title);
        diaryBean.setContent(content);
        if (diaryBean.getYear() == 0){
            ToastUtil.showToast("选择时间");
            return true;
        }
        if (diaryBean.getType() == 0){
            ToastUtil.showToast("选择心情");
            return true;
        }
        if (diaryBean.getMusic() == 0){
            ToastUtil.showToast("选择音乐");
            return true;
        }
        if (TextUtils.isEmpty(diaryBean.getTitle())){
            ToastUtil.showToast("输入标题");
            return true;
        }
        if (TextUtils.isEmpty(diaryBean.getTitle())){
            ToastUtil.showToast("输入内容");
            return true;
        }
        try {
            diaryDao.insert(diaryBean);
            finish();
        } catch (Exception e) {
            ToastUtil.showToast("保存失败");
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.stopPlay();
    }

    private void showExpression() {
        new XPopup.Builder(context)
                .dismissOnBackPressed(false)
                .asCustom(new ExpressionPopup(context,
                        res -> {
                            diaryBean.setType(res);
                            binding.diaryCreateType.setImageResource(res);
                            if (isFirst){
                                isFirst = false;
                                showTime();
                            }
                        }))
                .show();
    }

    private void showTime() {
        int type = TimePicker.TYPE_YEAR | TimePicker.TYPE_MONTH | TimePicker.TYPE_DAY;
        new TimePicker.Builder(mActivity, type, new TimePicker.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(TimePicker picker, Date date) {
                String time = format.format(date);
                binding.diaryCreateTime.setText(time);
                String[] strings = time.split("-");
                diaryBean.setYear(Integer.parseInt(strings[0]));
                diaryBean.setMonth(Integer.parseInt(strings[1]));
                diaryBean.setDay(Integer.parseInt(strings[2]));
            }
        })
                .setSelectedDate(System.currentTimeMillis())
                .create().show();
    }
}
