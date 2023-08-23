package com.jiazm.practice.service.Impl;

import com.jiazm.practice.service.UserService;
import com.jiazm.practice.entity.User;
import com.jiazm.practice.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author jiazhongmin
 * @Date 2023/8/23 15:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryByName(String username) {
        return userMapper.queryByName(username);
    }
}
