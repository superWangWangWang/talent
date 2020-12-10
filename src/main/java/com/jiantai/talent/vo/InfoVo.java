package com.jiantai.talent.vo;

public class InfoVo {
    //id                  int(11)
    private Integer id;
    //uid                 int(11)       对应user表id
    private Integer uid;
    //photo               varchar(73)   照片地址，talent/photo/md5(账号)/时间戳.jpg
    private String photo;
    //nick_name           varchar(32)   昵称
    private String nickName;
    //name                varchar(32)   真实姓名
    private String name;
    //sex                 int(1)        性别，0=女，1=男，2=其他
    private Integer sex;
    //age                 int(3)        年龄
    private Integer age;
    //native_place        varchar(32)   籍贯
    private String nativePlace;
    //national            varchar(32)   民族
    private String national;
    //height              int(3)        身高-单位cm
    private Integer height;
    //married             int(1)        是否结婚，0=未婚，1=已婚
    private Integer married;
    //health              int(1)        健康状况，0=优，1=良，2=中，3=差
    private Integer health;
    //phone_number        varchar(16)   联系电话
    private String phoneNumber;
    //intention           varchar(64)   求职意向/岗位--自己填自由发挥
    private String intention;
    //address             varchar(64)   现居住地址
    private String address;
    //skills_and_hobbies  varchar(64)   技能特长与爱好
    private String skillsAndHobbies;
    //education           int(3)        教育背景-最高学历对应学历表id）
    private Integer education;
    //work_experience     varchar(512)  工作经历
    private String workExperience;
    //expected_salary     int(11)       期望薪资
    private Integer expectedSalary;
    //watched             int(11)       被浏览数
    private Integer watched;

    //=================================企业的info
    @Override
    public String toString() {
        return "StaffInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", photo='" + photo + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", nativePlace='" + nativePlace + '\'' +
                ", national='" + national + '\'' +
                ", height=" + height +
                ", married=" + married +
                ", health=" + health +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", intention='" + intention + '\'' +
                ", address='" + address + '\'' +
                ", skillsAndHobbies='" + skillsAndHobbies + '\'' +
                ", education=" + education +
                ", workExperience='" + workExperience + '\'' +
                ", expectedSalary=" + expectedSalary +
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getMarried() {
        return married;
    }

    public void setMarried(Integer married) {
        this.married = married;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkillsAndHobbies() {
        return skillsAndHobbies;
    }

    public void setSkillsAndHobbies(String skillsAndHobbies) {
        this.skillsAndHobbies = skillsAndHobbies;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }


    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }
}
