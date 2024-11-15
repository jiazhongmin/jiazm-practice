package com.jiazm.practice.vo.menulist;

import lombok.Data;

import java.util.Date;

/**
 * menuList vo
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Data
public class MenuListVo {
	private static final long serialVersionUID = 1L;
	
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

