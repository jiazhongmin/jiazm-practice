package com.jiazm.practice.vo.fileresource;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * fileResource vo
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Data
public class FileResourceVo {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String fileName;
	private String sourceFileName;
	private String filePath;
	private String createBy;
	private Date createTime;
	private String modifyBy;
	private Date modifyTime;
	private Integer isDeleted;

}

