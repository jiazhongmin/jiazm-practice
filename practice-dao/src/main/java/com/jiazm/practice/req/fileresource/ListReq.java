package com.jiazm.practice.req.fileresource;

import javax.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
/**
 * fileResourceListReq
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Data
public class ListReq {
	private List<String> fileNames;
	private List<String> sourceFileNames;
	private List<String> filePaths;
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;
}

