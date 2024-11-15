package com.jiazm.practice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
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
        private String createBy;
        private Date createTime;
        private String modifyBy;
        private Date modifyTime;
        private Integer isDeleted;
    
}

