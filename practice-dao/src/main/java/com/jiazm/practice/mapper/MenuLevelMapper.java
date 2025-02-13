package com.jiazm.practice.mapper;

import com.jiazm.practice.entity.MenuLevel;
import com.jiazm.practice.req.menulevel.ListReq;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jiazm.practice.mapper.BaseMapper;


/**
 * menuLevel Mapper
 *
 * @author jiazm3
 * @date 2025-01-15 16:34:59
 */
@Mapper
public interface MenuLevelMapper extends BaseMapper<MenuLevel>{
    /**
     * 批量插入
     *
     * @return
     */
    Integer insertList(@Param("list") List<MenuLevel> list);
    /**
     * 条件多选查询
     *
     * @return
     */
    List<MenuLevel> selectByCondition(@Param("req") ListReq req);

}
