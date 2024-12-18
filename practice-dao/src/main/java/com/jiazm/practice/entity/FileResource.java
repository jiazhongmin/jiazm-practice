package com.jiazm.practice.entity;

import lombok.Data;

import java.util.Date;
/**
 * fileResource
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Data
public class FileResource{
	    private Integer id;
        private String fileName;
        private String sourceFileName;
        private String thumbnailFilePath;
        private String filePath;
        private String createBy;
        private Date createTime;
        private String modifyBy;
        private Date modifyTime;
        private Integer isDeleted;
    
}

