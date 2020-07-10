package com.local.project.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "user")
public class UserBean {

    @Id
    @Index(unique = true)//设置唯一性
    private String username;

    private String password;

    @Generated(hash = 661839116)
    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
