package com.jiazm.practice.service;

import com.jiazm.practice.req.menulist.DeleteReq;
import com.jiazm.practice.req.menulist.ListReq;
import com.jiazm.practice.req.menulist.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.vo.DictVo;
import com.jiazm.practice.vo.menulist.MenuListListVo;

import java.util.List;

/**
 * menuList Service
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
public interface MenuListService{
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
     GeneralResponse<MenuListListVo> list(ListReq req);
    /**
  	 * 删除数据
  	 *
  	 * @param req
  	 * @return
  	 */
     GeneralResponse<String> del(DeleteReq req);

    GeneralResponse<List<DictVo>> menuLevel();
}
