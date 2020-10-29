package com.jiantai.talent.service;


import com.jiantai.talent.entity.DataEntity;
import com.jiantai.talent.entity.Resume;
import com.jiantai.talent.entity.StaffInfo;
import com.jiantai.talent.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserService {
    User findUser(String account);
    void register(User user);
    void addUserNickName(User user);
    void updateResumeState(User user);
    List<DataEntity> getEducation();
    List<DataEntity> getExperience();
    List<DataEntity> getSalary();
    void addResume(Resume resume);
    List<Resume> findResume(Integer u_id);
    void deleteResume(String id,String u_id);
    void editResume(Resume resume);
    void addStaffPhoto(User user);
    void updateStaffInfo(StaffInfo staffInfo);
    StaffInfo getStaffInfoByUid(Integer uid);
}
