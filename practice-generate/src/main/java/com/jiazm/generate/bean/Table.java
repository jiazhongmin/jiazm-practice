package com.jiazm.generate.bean;
public class Table {

    /**
     * 表名(对应数据库表)
     */
    private String tableName;
    /**
     * 对应类名(可自动下划线转驼峰、首字母大写)
     */
    private String className;
    /**
     * 表描述
     */
    private String tableDescribe;
    /**
     * 类所属模块
     * 会根据模块自动创建到所属模块文件目录（需先创建业务模块，即pom中module）
     */
    private String module;
    /**
     * 是否需要创建 service 跟 controller
     */
    private Boolean flag;

    public Table(String tableName, String className, String tableDescribe, String module, Boolean flag) {
        this.tableName = tableName;
        this.className = className;
        this.tableDescribe = tableDescribe;
        this.module = module;
        this.flag = flag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableDescribe() {
        return tableDescribe;
    }

    public void setTableDescribe(String tableDescribe) {
        this.tableDescribe = tableDescribe;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
