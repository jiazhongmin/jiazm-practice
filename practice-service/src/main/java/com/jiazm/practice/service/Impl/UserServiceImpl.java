package com.jiazm.practice.service.Impl;

import com.jiazm.practice.entity.User;
import com.jiazm.practice.mapper.UserMapper;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

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

    @Override
    public GeneralResponse<List<User>> userList() {
        List<User> users = userMapper.userList();
        return GeneralResponse.success(users);
    }

}
