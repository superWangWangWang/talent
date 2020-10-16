package com.jiantai.talent.service.serviceImpl;

import com.jiantai.talent.entity.User;
import com.jiantai.talent.mapper.UserMapper;
import com.jiantai.talent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
