package com.jiazm.practice.vo.fileresource;

import lombok.Data;
import java.util.List;

/**
 * fileResourceListVo
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Data
public class FileResourceListVo{

	private List<FileResourceVo> list;
	private Integer total;

}

