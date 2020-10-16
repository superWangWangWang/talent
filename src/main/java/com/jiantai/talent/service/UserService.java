package com.jiantai.talent.service;


import com.jiantai.talent.entity.User;


public interface UserService {
    User findUser(String account);
    void register(User user);
    void addUserNickName(User user);
}
