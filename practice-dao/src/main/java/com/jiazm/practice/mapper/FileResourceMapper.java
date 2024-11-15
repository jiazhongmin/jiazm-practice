package com.jiazm.practice.mapper;

import com.jiazm.practice.entity.FileResource;
import com.jiazm.practice.req.fileresource.ListReq;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jiazm.practice.mapper.BaseMapper;


/**
 * fileResource Mapper
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Mapper
public interface FileResourceMapper extends BaseMapper<FileResource>{
    /**
     * 批量插入
     *
     * @return
     */
    Integer insertList(@Param("list") List<FileResource> list);
    /**
     * 条件多选查询
     *
     * @return
     */
    List<FileResource> selectByCondition(@Param("req") ListReq req);

}
