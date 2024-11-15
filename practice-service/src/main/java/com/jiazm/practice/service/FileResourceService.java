package com.jiazm.practice.service;

import com.jiazm.practice.req.fileresource.DeleteReq;
import com.jiazm.practice.req.fileresource.ListReq;
import com.jiazm.practice.req.fileresource.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.vo.UploadImageVo;
import com.jiazm.practice.vo.fileresource.FileResourceListVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * fileResource Service
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
public interface FileResourceService{
    /**
  	 * 更新数据
  	 *
  	 * @param req
  	 * @return
  	 */
     GeneralResponse<String> modify(ModifyReq req);
    /**
  	 * 列表数据
  	 *
  	 * @param req
  	 * @return
  	 */
     GeneralResponse<FileResourceListVo> list(ListReq req);
    /**
  	 * 删除数据
  	 *
  	 * @param req
  	 * @return
  	 */
     GeneralResponse<String> del(DeleteReq req);
	GeneralResponse<UploadImageVo> uploadFile(MultipartFile file);

}
