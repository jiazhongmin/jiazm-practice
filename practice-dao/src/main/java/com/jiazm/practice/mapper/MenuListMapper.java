package com.jiazm.practice.mapper;

import com.jiazm.practice.entity.MenuList;
import com.jiazm.practice.req.menulist.ListReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * menuList Mapper
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Mapper
public interface MenuListMapper extends BaseMapper<MenuList>{
    /**
     * 批量插入
     *
     * @return
     */
    Integer insertList(@Param("list") List<MenuList> list);
    /**
     * 条件多选查询
     *
     * @return
     */
    List<MenuList> selectByCondition(@Param("req") ListReq req);

}
