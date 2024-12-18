package com.jiazm.practice.entity;

import lombok.Data;

import java.util.Date;
/**
 * menuList
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Data
public class MenuList{
	    private Integer id;
        private String foodName;
        private String foodDesc;
        private String foodLevel;
        private String foodPictureUrl;
        private String createBy;
        private Date createTime;
        private String modifyBy;
        private Date modifyTime;
        private Integer isDeleted;
    
}

