package com.jiazm.practice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
/**
 * menuLevel
 *
 * @author jiazm3
 * @date 2025-01-15 16:34:59
 */
@Data
public class MenuLevel{
	    private Integer id;
        private String typeName;
        private String createBy;
        private Date createTime;
        private String modifyBy;
        private Date modifyTime;
        private Integer isDeleted;
    
}

