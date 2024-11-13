package com.jiazm.practice.mapper;

import com.jiazm.practice.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author jiazhongmin
 * @date 2023/8/23
 */
@Mapper
public interface UserMapper {


    int insert(User user);

    User queryByName(String username);

    List<User> userList();
}
