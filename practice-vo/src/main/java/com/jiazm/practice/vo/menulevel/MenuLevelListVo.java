package com.jiazm.practice.vo.menulevel;

import lombok.Data;
import java.util.List;

/**
 * menuLevelListVo
 *
 * @author jiazm3
 * @date 2025-01-15 16:34:59
 */
@Data
public class MenuLevelListVo{

	private List<MenuLevelVo> list;
	private Integer total;

}

