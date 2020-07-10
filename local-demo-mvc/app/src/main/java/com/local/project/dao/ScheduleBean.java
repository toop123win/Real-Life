package com.local.project.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(nameInDb = "schedule")
public class ScheduleBean {

    @Id
    @Index(unique = true)//设置唯一性
    private long id;
    private int year;
    private int month;
    private int day;
    private String username;
    private String time;
    private String type;
    private String content;
    @Generated(hash = 894382589)
    public ScheduleBean(long id, int year, int month, int day, String username,
            String time, String type, String content) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.username = username;
        this.time = time;
        this.type = type;
        this.content = content;
    }
    @Generated(hash = 1005095379)
    public ScheduleBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return this.month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
