package com.jiazm.generate.util;

import java.util.ResourceBundle;

/**
 * 构建参数
 *
 * @author LiLong10
 * @date 2019-03-25
 * Copyright 2019 Lenovo GSC Technical Service
 */
public class CodeResourceUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

    public static String DIVER_NAME;

    public static String URL;

    public static String USERNAME;

    public static String PASSWORD;

    public static String DATABASE_NAME;

    public static String DATABASE_TYPE = "mysql";
    public static String DATABASE_TYPE_MYSQL = "mysql";
    public static String DATABASE_TYPE_ORACLE = "oracle";
    public static String DATABASE_TYPE_POSTGRESQL = "postgresql";

    public static String web_root_package;

    public static String source_root_package;

    public static String bussiPackage;
    public static String bussiPackageUrl;
    public static String viewPackage;
    public static String viewPackageUrl;

    public static String entity_package = "entity";

    public static String page_package = "page";

    public static String ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/" + entity_package + "/";

    public static String PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/" + page_package + "/";

    public static String ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

    public static String PAGE_URL_INX = bussiPackage + "." + page_package + ".";
    public static String CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

    static {
        URL = getURL();
        USERNAME = getUSERNAME();
        PASSWORD = getPASSWORD();
        DATABASE_NAME = getDATABASE_NAME();

        source_root_package = getSourceRootPackage();
        web_root_package = getWebRootPackage().replace(".", "/");
        bussiPackage = getBussiPackage();
        bussiPackageUrl = bussiPackage.replace(".", "/");
        // viewPackageUrl = viewPackage.replace(".", "/");
        source_root_package = source_root_package.replace(".", "/");

        if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0)) {
            DATABASE_TYPE = DATABASE_TYPE_MYSQL;
            DIVER_NAME = "com.mysql.cj.jdbc.Driver";
        } else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
            DATABASE_TYPE = DATABASE_TYPE_ORACLE;
            DIVER_NAME = "oracle.jdbc.driver.OracleDriver";
        } else if ((URL.indexOf("postgresql") >= 0) || (URL.indexOf("POSTGRESQL") >= 0)) {
            DATABASE_TYPE = DATABASE_TYPE_POSTGRESQL;
            DIVER_NAME = "org.postgresql.Driver";
        }
    }

    public static final String getURL() {
        return bundle.getString("url");
    }

    public static final String getUSERNAME() {
        return bundle.getString("username");
    }

    public static final String getPASSWORD() {
        return bundle.getString("password");
    }

    public static final String getDATABASE_NAME() {
        return bundle.getString("database_name");
    }

    private static String getBussiPackage() {
        return bundle.getString("bussi_package");
    }

    public static final String getSourceRootPackage() {
        return bundle.getString("source_root_package");
    }

    public static final String getWebRootPackage() {
        return bundle.getString("webroot_package");
    }

}
