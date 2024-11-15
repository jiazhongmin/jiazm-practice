package com.jiazm.practice.mapper;


import java.util.List;

/**
 * 基础DAO接口<br>
 * 处理公用的CRUD、分页等
 *
 * @param <T>
 * @author LiLong 2014-7-28
 */
public interface BaseMapper<T> {

    /**
     * 保存实体
     *
     * @param entity 实体
     * @return
     */
    Integer add(T entity);

    /**
     * 删除实体,根据主键
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 逻辑删除,根据主键
     *
     * @param id
     * @return
     */
    Integer isDelete(Integer id);

    /**
     * 更新实体,根据主键
     *
     * @param entity 实体
     * @return
     */
    Integer update(T entity);

    /**
     * 更新实体,根据UniqueIndex
     *
     * @param entity 实体
     * @return
     */
    Integer updateByUnique(T entity);

    /**
     * 返回实体,根据主键
     *
     * @param id 主键
     * @return
     */
    T findById(Integer id);

    /**
     * 返回实体List,根据条件
     *
     * @param entity 实体
     * @return
     */
    List<T> findList(T entity);

    /**
     * 返回实体List,根据条件,模糊匹配
     *
     * @param entity 实体
     * @return
     */
    List<T> findListByLike(T entity);

}
