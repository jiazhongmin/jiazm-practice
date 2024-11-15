package com.jiazm.practice.req.fileresource;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * fileResourceModifyReq
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Data
public class ModifyReq {
	private Integer id;
	private String fileName;
	private String sourceFileName;
	private String filePath;

}

