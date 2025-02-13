package com.jiazm.practice.req.menulist;

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
	private List<Integer> foodLevels;
    private Integer pageNum;
    private Integer pageSize;
}

