package com.jiazm.practice.mapper;

import com.jiazm.practice.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jiazhongmin
 * @date 2023/8/23
 */
@Mapper
public interface UserLogMapper {

    int insert(UserLog userLog);
}
