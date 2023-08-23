package com.jiazm.practice.service.Impl;

import com.jiazm.practice.service.UserLogService;
import com.jiazm.practice.entity.UserLog;
import com.jiazm.practice.mapper.UserLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author jiazhongmin
 * @Date 2023/8/23 15:33
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Resource
    private UserLogMapper userLogMapper;

    @Override
    public boolean save(UserLog userLog) {
       return userLogMapper.insert(userLog) > 0;
    }
}
