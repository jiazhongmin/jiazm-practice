package com.jiazm.practice.req.menulist;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * menuListModifyReq
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Data
public class ModifyReq {
	private Integer id;
	private String foodName;
	private String foodDesc;
	private String foodLevel;

}

