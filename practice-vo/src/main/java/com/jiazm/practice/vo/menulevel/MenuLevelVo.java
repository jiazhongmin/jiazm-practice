package com.jiazm.practice.vo.menulevel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * menuLevel vo
 *
 * @author jiazm3
 * @date 2025-01-15 16:34:59
 */
@Data
public class MenuLevelVo {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String typeName;
	private String createBy;
	private Date createTime;
	private String modifyBy;
	private Date modifyTime;
	private Integer isDeleted;

}

