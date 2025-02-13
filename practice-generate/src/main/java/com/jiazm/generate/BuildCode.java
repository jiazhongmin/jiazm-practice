package com.jiazm.generate;

import com.google.common.collect.Lists;
import com.jiazm.generate.bean.Table;

import java.util.List;

/**
 * 项目构建函数入口
 *
 * @author jiazm3
 * @date 2021-12-06
 */
public class BuildCode {

    public static void main(String[] args) {
        List<Table> tableList = Lists.newArrayList();
        tableList.add(new Table("menu_level", "menuLevel", "menuLevel", "jiazm-practice", false));
        BuildCodeUtil.build(tableList);
    }
}