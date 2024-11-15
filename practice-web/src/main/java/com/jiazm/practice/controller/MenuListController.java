package com.jiazm.practice.controller;

import com.jiazm.practice.req.menulist.DeleteReq;
import com.jiazm.practice.req.menulist.ListReq;
import com.jiazm.practice.req.menulist.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.service.MenuListService;
import com.jiazm.practice.vo.menulist.MenuListListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * menuList Controller
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@RestController
@RequestMapping("/menuList")
public class MenuListController{

	@Resource
	private MenuListService menuListService;

	/**
	 * 更新数据
	 *
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/modify")
	public GeneralResponse<String> modify(@RequestBody @Valid ModifyReq req) {
		return menuListService.modify(req);
	}

	/**
	 * 列表数据
	 *
	 * @param req
	 * @return
	 */
	@PostMapping("/list")
	public GeneralResponse<MenuListListVo> list(@RequestBody @Valid ListReq req) {
		return menuListService.list(req);
	}

	/**
      * 删除数据
      *
      * @param req
      * @return
     */
    @PostMapping("/del")
    public GeneralResponse<String> del(@RequestBody @Valid DeleteReq req) {
    	return menuListService.del(req);
    }
}
