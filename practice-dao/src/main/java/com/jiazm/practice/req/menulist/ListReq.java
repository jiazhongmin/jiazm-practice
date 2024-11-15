package com.jiazm.practice.req.menulist;

import javax.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
/**
 * menuListListReq
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Data
public class ListReq {
	private List<String> foodNames;
	private List<String> foodDescs;
	private List<String> foodLevels;
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;
}

