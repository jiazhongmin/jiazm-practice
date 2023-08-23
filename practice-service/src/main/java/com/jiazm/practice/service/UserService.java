package com.jiazm.practice.service;


import com.jiazm.practice.entity.User;

/**
 * @Author jiazhongmin
 * @Date 2023/8/23 15:33
 */
public interface UserService{


    User queryByName(String username);
}
