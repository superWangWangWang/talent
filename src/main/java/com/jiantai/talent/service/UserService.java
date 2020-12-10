package com.jiantai.talent.service;


import com.jiantai.talent.entity.*;


import java.util.List;

public interface UserService {
    User findUserByAccount(String account);
    User findUserById(Integer id);
    void updateUser(User user);
    void addUser(User user);
    StaffInfo findStaffInfoByUid(Integer uid);
    void addStaffInfo( User user);
    void updateStaffInfo(StaffInfo staffInfo);
    List<Education> getEducation();
    List<Experience> getExperience();
    List<Salary> getSalary();
    List<Resume> findResume(Integer uid);
    void addResume(Resume resume);
    void deleteResume(Resume resume);
    void editResume(Resume resume);
    BossInfo findBossInfoByUid(User user);
    void updateBossInfo(BossInfo bossInfo);
    List<Welfare> getWelfare();
    List<Recruit> getRecruit();
    void addRecruit(Recruit recruit);
    List<Recruit> findRecruitByUid(User user);
    void updateRecruit(Recruit recruit);
    Recruit findRecruitById(Recruit recruit);
    List<Notice> getNotice();
}
