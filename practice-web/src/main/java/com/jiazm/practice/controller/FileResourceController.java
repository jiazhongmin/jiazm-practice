package com.jiazm.practice.controller;

import com.jiazm.practice.service.FileResourceService;
import javax.annotation.Resource;

import com.jiazm.practice.vo.UploadImageVo;
import org.springframework.web.bind.annotation.*;
import com.jiazm.practice.req.fileresource.*;
import com.jiazm.practice.vo.fileresource.*;
import com.jiazm.practice.response.GeneralResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

/**
 * fileResource Controller
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@RestController
@RequestMapping("/fileResource")
public class FileResourceController{

	@Resource
	private FileResourceService fileResourceService;

	/**
	 * 更新数据
	 *
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/modify")
	public GeneralResponse<String> modify(@RequestBody @Valid ModifyReq req) {
		return fileResourceService.modify(req);
	}

	/**
	 * 列表数据
	 *
	 * @param req
	 * @return
	 */
	@PostMapping("/list")
	public GeneralResponse<FileResourceListVo> list(@RequestBody @Valid ListReq req) {
		return fileResourceService.list(req);
	}

	/**
      * 删除数据
      *
      * @param req
      * @return
     */
    @PostMapping("/del")
    public GeneralResponse<String> del(@RequestBody @Valid DeleteReq req) {
    	return fileResourceService.del(req);
    }
	/**
	 * 上传Interface图片
	 *
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadFile")
	public GeneralResponse<UploadImageVo> uploadFile(@RequestParam("file") MultipartFile file) {
		return fileResourceService.uploadFile(file);
	}
}
