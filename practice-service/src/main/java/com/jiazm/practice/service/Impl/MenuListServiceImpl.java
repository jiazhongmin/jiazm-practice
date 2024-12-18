package com.jiazm.practice.service.Impl;

import com.github.pagehelper.PageInfo;
import com.jiazm.practice.commons.SetValueUtils;
import com.jiazm.practice.entity.MenuList;
import com.jiazm.practice.exception.BaseException;
import com.jiazm.practice.mapper.MenuListMapper;
import com.jiazm.practice.req.menulist.DeleteReq;
import com.jiazm.practice.req.menulist.ListReq;
import com.jiazm.practice.req.menulist.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.service.MenuListService;
import com.jiazm.practice.utils.CopyClass;
import com.jiazm.practice.vo.menulist.MenuListListVo;
import com.jiazm.practice.vo.menulist.MenuListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.pagehelper.page.PageMethod.offsetPage;

/**
 * menuList ServiceImpl
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@Service("menuListService")
@Slf4j
public class MenuListServiceImpl implements MenuListService {

    @Resource
    private MenuListMapper menuListMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResponse<String> modify(ModifyReq req) {
        //获取用户itCode
        String itCode = "jiazm3";
        Date date = new Date();
        //验证是否存在条件匹配的数据
        verifyDataExist(req);
        if (Objects.nonNull(req.getId())) {
            MenuList menuList = menuListMapper.findById(req.getId());
            MenuList menuListUpdate = new CopyClass<ModifyReq, MenuList>().copyBean(req, MenuList.class);
            menuListUpdate.setId(menuList.getId());
            SetValueUtils.setMethodUpdateValVoid(menuListUpdate, itCode, date);
            menuListMapper.update(menuListUpdate);
        } else {
            MenuList menuListAdd = new CopyClass<ModifyReq, MenuList>().copyBean(req, MenuList.class);
            SetValueUtils.setMethodCreateValVoid(menuListAdd, itCode, date);
            menuListMapper.add(menuListAdd);
        }
        return GeneralResponse.success("modify success");
    }

    @Override
    public GeneralResponse<MenuListListVo> list(ListReq req) {
        MenuListListVo vo = new MenuListListVo();
        if (Objects.nonNull(req.getPageNum()) && Objects.nonNull(req.getPageSize())) {
            req.setPageNum((req.getPageNum() - 1) * req.getPageSize());
        }
        offsetPage(req.getPageNum(), req.getPageSize());
        List<MenuList> vos = menuListMapper.selectByCondition(req);
        PageInfo<MenuList> pageInfo = new PageInfo<>(vos);
        List<MenuListVo> menuListVos = new CopyClass<MenuList, MenuListVo>().copyListNull(pageInfo.getList(), MenuListVo.class);
        vo.setList(menuListVos);
        vo.setTotal((int) pageInfo.getTotal());
        return GeneralResponse.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResponse<String> del(DeleteReq req) {
        String itCode = "jiazm3";
        Date date = new Date();
        MenuList menuList = menuListMapper.findById(req.getId());
        SetValueUtils.setMethodDelValVoid(menuList, itCode, date);
        menuListMapper.update(menuList);
        return GeneralResponse.success("delete success");
    }


    private void verifyDataExist(ModifyReq req) {
        MenuList findMenuList = new MenuList();
        findMenuList.setIsDeleted(0);
        findMenuList.setFoodName(req.getFoodName());
        List<MenuList> menuListList = menuListMapper.findList(findMenuList);
        if (Objects.isNull(req.getId())) {
            if (CollectionUtils.isNotEmpty(menuListList)) {
                throw new BaseException("-999", "这个菜单已经存在!");
            }
        } else {
            List<MenuList> resultList = menuListList.stream().filter(menuList -> !menuList.getId().equals(req.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(resultList)) {
                throw new BaseException("-999", "这个菜单已经存在!");
            }
        }
    }
}
