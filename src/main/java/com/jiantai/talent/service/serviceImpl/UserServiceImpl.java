package com.jiantai.talent.service.serviceImpl;

import com.jiantai.talent.entity.*;
import com.jiantai.talent.mapper.UserMapper;
import com.jiantai.talent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public StaffInfo findStaffInfoByUid(Integer uid) {
        return userMapper.findStaffInfoByUid(uid);
    }

    @Override
    public void addStaffInfo(User user) {
        userMapper.addStaffInfo(user);
    }

    @Override
    public void updateStaffInfo(StaffInfo staffInfo) {
        userMapper.updateStaffInfo(staffInfo);
    }

    @Override
    public List<Education> getEducation() {
        return userMapper.getEducation();
    }

    @Override
    public List<Experience> getExperience() {
        return userMapper.getExperience();
    }

    @Override
    public List<Salary> getSalary() {
        return userMapper.getSalary();
    }

    @Override
    public List<Resume> findResume(Integer uid) {
        return userMapper.findResume(uid);
    }

    @Override
    public void addResume(Resume resume) {
        userMapper.addResume(resume);
    }

    @Override
    public void deleteResume(Resume resume) {
        userMapper.deleteResume(resume);
    }

    @Override
    public void editResume(Resume resume) {
        userMapper.editResume(resume);
    }

    @Override
    public BossInfo findBossInfoByUid(User user) {
        return userMapper.findBossInfoByUid(user);
    }

    @Override
    public void updateBossInfo(BossInfo bossInfo) {
        userMapper.updateBossInfo(bossInfo);
    }

    @Override
    public List<Welfare> getWelfare() {
        return userMapper.getWelfare();
    }

    @Override
    public List<Recruit> getRecruit() {
        return userMapper.getRecruit();
    }

    @Override
    public void addRecruit(Recruit recruit) {
        userMapper.addRecruit(recruit);
    }

    @Override
    public List<Recruit> findRecruitByUid(User user) {
        return userMapper.findRecruitByUid(user);
    }

    @Override
    public void updateRecruit(Recruit recruit) {
        userMapper.updateRecruit(recruit);
    }

    @Override
    public Recruit findRecruitById(Recruit recruit) {
        return userMapper.findRecruitById(recruit);
    }

    @Override
    public List<Notice> getNotice() {
        return userMapper.getNotice();
    }
}
