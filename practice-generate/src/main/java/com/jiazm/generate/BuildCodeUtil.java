package com.jiazm.generate;

import com.jiazm.generate.factory.CodeGenerateFactory;
import com.jiazm.generate.bean.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 构建函数入口
 *
 * @author jiazm3
 * @date 2021-12-06
 */
public class BuildCodeUtil {

    private static final Logger log = LoggerFactory.getLogger(BuildCodeUtil.class);

    /**
     * 构建生成代码，会根据模块自动创建到所属模块文件目录（需先创建业务模块，即pom中module）
     *
     * @param tableList
     */
    public static void build(List<Table> tableList) {
        log.info("---------------------------- 代码生成开始 ---------------------------");
        tableList.forEach(table -> CodeGenerateFactory.codeGenerate(table.getTableName(), table.getClassName(), table.getTableDescribe(), table.getModule(),table.getFlag()));
        log.info("---------------------------- 代码生成完毕 ---------------------------");
    }
}