package com.jiantai.talent.entity;

public class BossInfo {
    //idint(11) NOT NULL
    private Integer id;
    //uidint(11) NULL对应user表id
    private Integer uid;
    //photovarchar(128) NULL照片地址，talent/photo/boss/md5(账号)/时间戳.jpg
    private String photo;
    //namevarchar(32) NULL企业名
    private String name;
    //phone_numbervarchar(16) NULL联系电话
    private String phoneNumber;
    //addressvarchar(64) NULL地址
    private String address;
    //staff_countint(11) NULL企业人数
    private Integer staffCount;
    //credit_codevarchar(20) NULL统一社会信用代码
    private String creditCode;
    //websitevarchar(64) NULL官网
    private String website;
    //被浏览数
    private Integer watched;

    @Override
    public String toString() {
        return "BossInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", staffCount=" + staffCount +
                ", creditCode='" + creditCode + '\'' +
                ", website='" + website + '\'' +
                ", watched=" + watched +
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(Integer staffCount) {
        this.staffCount = staffCount;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }
}
