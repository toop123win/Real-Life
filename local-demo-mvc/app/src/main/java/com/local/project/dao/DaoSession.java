package com.local.project.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.local.project.dao.DiaryBean;
import com.local.project.dao.ScheduleBean;
import com.local.project.dao.UserBean;

import com.local.project.dao.DiaryBeanDao;
import com.local.project.dao.ScheduleBeanDao;
import com.local.project.dao.UserBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig diaryBeanDaoConfig;
    private final DaoConfig scheduleBeanDaoConfig;
    private final DaoConfig userBeanDaoConfig;

    private final DiaryBeanDao diaryBeanDao;
    private final ScheduleBeanDao scheduleBeanDao;
    private final UserBeanDao userBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        diaryBeanDaoConfig = daoConfigMap.get(DiaryBeanDao.class).clone();
        diaryBeanDaoConfig.initIdentityScope(type);

        scheduleBeanDaoConfig = daoConfigMap.get(ScheduleBeanDao.class).clone();
        scheduleBeanDaoConfig.initIdentityScope(type);

        userBeanDaoConfig = daoConfigMap.get(UserBeanDao.class).clone();
        userBeanDaoConfig.initIdentityScope(type);

        diaryBeanDao = new DiaryBeanDao(diaryBeanDaoConfig, this);
        scheduleBeanDao = new ScheduleBeanDao(scheduleBeanDaoConfig, this);
        userBeanDao = new UserBeanDao(userBeanDaoConfig, this);

        registerDao(DiaryBean.class, diaryBeanDao);
        registerDao(ScheduleBean.class, scheduleBeanDao);
        registerDao(UserBean.class, userBeanDao);
    }
    
    public void clear() {
        diaryBeanDaoConfig.clearIdentityScope();
        scheduleBeanDaoConfig.clearIdentityScope();
        userBeanDaoConfig.clearIdentityScope();
    }

    public DiaryBeanDao getDiaryBeanDao() {
        return diaryBeanDao;
    }

    public ScheduleBeanDao getScheduleBeanDao() {
        return scheduleBeanDao;
    }

    public UserBeanDao getUserBeanDao() {
        return userBeanDao;
    }

}
