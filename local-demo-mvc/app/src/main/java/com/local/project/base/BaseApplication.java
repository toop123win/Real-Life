package com.local.project.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.local.project.R;
import com.local.project.dao.DaoMaster;
import com.local.project.dao.DaoSession;
import com.lxj.xpopup.XPopup;

import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

public class BaseApplication extends Application {

    private static BaseApplication application;

    private static DaoSession mDaoSession;

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initXpopup();
        initDao();
    }

    private void initDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application,"app_green.db");
        //获取可写数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }

    //XPopup颜色设置
    private void initXpopup() {
        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));
        XPopup.setShadowBgColor(Color.parseColor("#30000000"));
        PickerView.sCenterColor = 0xFFF7B200;
        DefaultCenterDecoration.sDefaultLineColor = 0xFFF7B200;
    }

}
