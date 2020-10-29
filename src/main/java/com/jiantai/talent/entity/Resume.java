package com.jiantai.talent.entity;

import java.util.Date;

/**
 * 简历表实体类
 */
public class Resume {
    //idint(11) NOT NULL
    private Integer id;
    //u_idint(11) NULL用户id
    private Integer uid;
    //namevarchar(64) NULL名字
    private String name;
    //sexint(2) NULL性别
    private Integer sex;
    //ageint(3) NULL年龄
    private Integer age;
    //educationint(3) NULL学历--学历表id
    private Integer education;
    //intentionvarchar(256) NULL求职意向
    private String intention;
    //experienceint(3) NULL工作年限--年限表id
    private Integer experience;
    //salaryint(11) NULL期望薪资--薪资表id
    private Integer salary;
    //phone_numbervarchar(16) NULL联系电话
    private String phoneNumber;
    //work_experiencevarchar(1024) NULL工作经历
    private String workExperience;
    //education_experiencevarchar(1024) NULL教育经历
    private String educationExperience;
    //skillvarchar(128) NULL专业技能
    private String skill;
    //introductionvarchar(512) NULL自我介绍
    private String introduction;
    //create_timetimestamp NULL
    private Date createTime;
    //update_timetimestamp NULL
    private Date updateTime;
    private Integer delete;

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", education=" + education +
                ", intention='" + intention + '\'' +
                ", experience=" + experience +
                ", salary=" + salary +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", educationExperience='" + educationExperience + '\'' +
                ", skill='" + skill + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delete=" + delete +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getEducationExperience() {
        return educationExperience;
    }

    public void setEducationExperience(String educationExperience) {
        this.educationExperience = educationExperience;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }
}
