package com.jiazm.practice.service;


import com.jiazm.practice.entity.User;
import com.jiazm.practice.response.GeneralResponse;

import java.util.List;

/**
 * @Author jiazhongmin
 * @Date 2023/8/23 15:33
 */
public interface UserService{


    User queryByName(String username);

    GeneralResponse<List<User>> userList();
}
