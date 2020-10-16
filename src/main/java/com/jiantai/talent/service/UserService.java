package com.jiantai.talent.service;


import com.jiantai.talent.entity.User;
import org.apache.ibatis.annotations.Param;


public interface UserService {
    User findUser(String account);
    void register(User user);
    void addUserNickName(User user);
    void updateResumeState(User user);
}
