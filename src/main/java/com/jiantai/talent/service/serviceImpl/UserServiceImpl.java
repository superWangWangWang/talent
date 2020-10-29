package com.jiantai.talent.service.serviceImpl;

import com.jiantai.talent.entity.DataEntity;
import com.jiantai.talent.entity.Resume;
import com.jiantai.talent.entity.StaffInfo;
import com.jiantai.talent.entity.User;
import com.jiantai.talent.mapper.UserMapper;
import com.jiantai.talent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUser(String account) {
        return userMapper.findUser(account);
    }

    @Override
    public void register(User user) {
        userMapper.register(user);
    }

    @Override
    public void addUserNickName(User user) {
        userMapper.addUserNickName(user);
    }

    @Override
    public void updateResumeState(User user) {
        userMapper.updateResumeState(user);
    }

    @Override
    public List<DataEntity> getEducation() {
        return userMapper.getEducation();
    }

    @Override
    public List<DataEntity> getExperience() {
        return userMapper.getExperience();
    }

    @Override
    public List<DataEntity> getSalary() {
        return userMapper.getSalary();
    }

    @Override
    public void addResume(Resume resume) {
        userMapper.addResume(resume);
    }

    @Override
    public List<Resume> findResume(Integer u_id) {
        return userMapper.findResume(u_id);
    }

    @Override
    public void deleteResume(String id, String u_id) {
        userMapper.deleteResume(id,u_id);
    }

    @Override
    public void editResume(Resume resume) {
        userMapper.editResume(resume);
    }

    @Override
    public void addStaffPhoto(User user) {
        userMapper.addStaffPhoto(user);
    }

    @Override
    public StaffInfo getStaffInfoByUid(Integer uid) {
        return userMapper.getStaffInfoByUid(uid);
    }

    @Override
    public void updateStaffInfo(StaffInfo staffInfo) {
        userMapper.updateStaffInfo(staffInfo);
    }
}
