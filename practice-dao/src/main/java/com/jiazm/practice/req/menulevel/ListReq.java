package com.jiazm.practice.req.menulevel;

import javax.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
/**
 * menuLevelListReq
 *
 * @author jiazm3
 * @date 2025-01-15 16:34:59
 */
@Data
public class ListReq {
	private List<String> typeNames;
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;
}

