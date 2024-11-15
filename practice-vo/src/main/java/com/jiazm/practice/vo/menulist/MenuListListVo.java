package com.jiazm.practice.vo.menulist;

import lombok.Data;
import java.util.List;

/**
 * menuListListVo
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Data
public class MenuListListVo{

	private List<MenuListVo> list;
	private Integer total;

}

