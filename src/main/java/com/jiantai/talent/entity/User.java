package com.jiantai.talent.entity;

import java.util.Date;

/**
 * 用户实体类
 */
public class User {
    //    idint(11) NOT NULL
    private Integer id;
    //    accountvarchar(16) NULL登录账号
    private String account;
    //    pwdvarchar(32) NULL登录密码
    private String pwd;
    //    typeint(1) NULL1=求职者，2=招聘者，3=管理员，4=超级管理员（不作日志记录）
    private Integer type;
    //    register_timetimestamp NOT NULL注册时间
    private Date registerTime;
    //    login_last_timetimestamp NOT NULL最近一次登录时间
    private Date loginLastTime;
    //    login_countint(11) NULL登录次数
    private Integer loginCount;
    //state int(1) NULL状态，0=正常，1=异常不允许登录
    private Integer state;
    //0=公开，1=隐藏
    private Integer show;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", type=" + type +
                ", registerTime=" + registerTime +
                ", loginLastTime=" + loginLastTime +
                ", loginCount=" + loginCount +
                ", state=" + state +
                ", show=" + show +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLoginLastTime() {
        return loginLastTime;
    }

    public void setLoginLastTime(Date loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getResumeState() {
        return show;
    }

    public void setResumeState(Integer show) {
        this.show = show;
    }
}

