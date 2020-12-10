package com.jiantai.talent.entity;

import java.util.Date;

/**
 * 招聘实体类
 */
public class Recruit {
    //id            int(11)
    private Integer id;
    //uid           int(11)       user id
    private Integer uid;
    //title         varchar(64)   标题
    private String title;
    //job           varchar(64)   职位/岗位
    private String job;
    //salary        int(11)       薪资
    private Integer salary;
    //number        int(11)       招聘人数
    private Integer number;
    //education     int(11)       学历要求（查学历表）
    private Integer education;
    //experience    int(11)       工作年限要求（查经验表）
    private Integer experience;
    //address       varchar(128)  工作地址
    private String address;
    //welfare       varchar(128)  住房补贴|年底双薪|五险
    private String welfare;
    //work          varchar(512)  工作要求
    private String work;
    //contact       varchar(16)   联系人
    private String contact;
    //telephone     varchar(32)   联系电话
    private String telephone;
    //create_time   timestamp     创建时间
    private Date createTime;
    //update_time  timestamp     发布时间
    private Date updateTime;
    //watched       int(11)       被浏览数
    private Integer watched;
    //show          int(2)        0=展示，1=隐藏
    private Integer show;

    @Override
    public String toString() {
        return "Recruit{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", number=" + number +
                ", education=" + education +
                ", experience=" + experience +
                ", address='" + address + '\'' +
                ", welfare='" + welfare + '\'' +
                ", work='" + work + '\'' +
                ", contact='" + contact + '\'' +
                ", telephone='" + telephone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", watched=" + watched +
                ", show=" + show +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }
}
