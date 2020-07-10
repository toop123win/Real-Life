package com.local.project.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(nameInDb = "diary")
public class DiaryBean {

    @Id
    @Index(unique = true)//设置唯一性
    private long id;
    private int year;
    private int month;
    private int day;
    private String username;
    private int type;
    private int music;
    private String title;
    private String content;
    @Generated(hash = 1578709856)
    public DiaryBean(long id, int year, int month, int day, String username,
            int type, int music, String title, String content) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.username = username;
        this.type = type;
        this.music = music;
        this.title = title;
        this.content = content;
    }
    @Generated(hash = 1749744078)
    public DiaryBean() {
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
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getMusic() {
        return this.music;
    }
    public void setMusic(int music) {
        this.music = music;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    

}
