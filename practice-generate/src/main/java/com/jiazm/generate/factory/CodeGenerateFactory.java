package com.jiazm.generate.factory;

import com.jiazm.generate.bean.ColumnData;
import com.jiazm.generate.util.CodeResourceUtil;
import com.jiazm.generate.util.CommonPageParser;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 构建工厂
 *
 * @author jiazm3
 * @date 2021-12-06
 */
public class CodeGenerateFactory {

    private static final Logger log = LoggerFactory.getLogger(CodeGenerateFactory.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    private static String buss_package = CodeResourceUtil.bussiPackage;
    private static String source_root_package = CodeResourceUtil.source_root_package;
    private static String bussiPackageUrl = CodeResourceUtil.bussiPackageUrl;
    private static String projectPath = getProjectPath();

    public static void codeGenerate(String tableName, String className, String tableDes, String module, Boolean serviceControllerFlag) {

        try {
            CodeGenerateBuild codeGenerateBuild = new CodeGenerateBuild();
            codeGenerateBuild.setMysqlInfo(url, username, passWord);

            String[] moduleSplit = module.split("-");
            String modulePackage = moduleSplit.length > 0 ? moduleSplit[1] : module;
            className = codeGenerateBuild.getTablesNameToClassName(className);
            String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1);

            List<ColumnData> columnDatas = codeGenerateBuild.getColumnDatas(tableName);
            Map<String, Object> sqlMap = codeGenerateBuild.getAutoCreateSql(tableName, columnDatas);
            List<ColumnData> uniqueIndexColumns = codeGenerateBuild.getUniqueIndexColumn(tableName, columnDatas);

            VelocityContext context = new VelocityContext();
            context.put("databaseType", CodeResourceUtil.DATABASE_TYPE);
            context.put("className", className);
            context.put("lowerName", lowerName);
            context.put("fullLowerName", className.toLowerCase());
            context.put("codeName", tableDes);
            context.put("tableName", tableName);
            context.put("bussPackage", buss_package + "." + modulePackage);
            context.put("dateTime", sdf.format(new Date()));
            context.put("keyType", "02");
            context.put("editFlag", "editFlag");
            context.put("moduleShort", modulePackage);
            context.put("SQL", sqlMap);
            context.put("columnDatas", columnDatas);
            context.put("uniqueIndexColumns", uniqueIndexColumns);
            context.put("dollar", "$");

            //TODO ECC TableName
            String[] split = tableDes.split("##");
            if (split.length > 1) context.put("eccTableName", split[1]);

            String javaPath = projectPath;
            String javaSrcPath = source_root_package + "/" + bussiPackageUrl + "/" + modulePackage;

            //String javaEntityParentPath = MessageFormat.format("/{0}-dao/{1}/entity/parent/{2}Parent.java", module, javaSrcPath, className);
            String javaEntityPath = MessageFormat.format("/practice-dao/{1}/entity/{2}.java", module, javaSrcPath, className);
            String javaMapperPath = MessageFormat.format("/practice-dao/{1}/mapper/{2}Mapper.java", module, javaSrcPath, className);
            String javaMapperXmlPath = MessageFormat.format("/practice-dao/{1}/mapper/{2}Mapper.xml", module, javaSrcPath.replace("/java/", "/resources/"), className);
            String javaVOPath = MessageFormat.format("/practice-vo/{1}/vo/" + className.toLowerCase() + "/{2}Vo.java", module, javaSrcPath, className);
            String javaListVoPath = MessageFormat.format("/practice-vo/{1}/vo/" + className.toLowerCase() + "/{2}ListVo.java", module, javaSrcPath, className);
            String javaListReqPath = MessageFormat.format("/practice-dao/{1}/req/" + className.toLowerCase() + "/ListReq.java", module, javaSrcPath, className);
            String javaModifyPath = MessageFormat.format("/practice-vo/{1}/req/" + className.toLowerCase() + "/ModifyReq.java", module, javaSrcPath, className);
            String javaDeletePath = MessageFormat.format("/practice-vo/{1}/req/" + className.toLowerCase() + "/DeleteReq.java", module, javaSrcPath, className);
            String javaServicePath = MessageFormat.format("/practice-service/{1}/service/{2}Service.java", module, javaSrcPath, className);
            String javaServiceImplPath = MessageFormat.format("/practice-service/{1}/service/impl/{2}ServiceImpl.java", module, javaSrcPath, className);
            String javaControllerPath = MessageFormat.format("/practice-web/{1}/controller/{2}Controller.java", module, javaSrcPath, className);
            //ES
            String javaEsRepositoryPath = MessageFormat.format("/{0}-dao/{1}/repository/{2}Repository.java", module, javaSrcPath, className);
            String javaEsEntityParentPath = MessageFormat.format("/{0}-dao/{1}/entity/parent/{2}EntityParent.java", module, javaSrcPath, className);
            String javaEsServicePath = MessageFormat.format("/{0}-service/{1}/service/{2}Service.java", module, javaSrcPath, className);
            String javaEsServiceImplPath = MessageFormat.format("/{0}-service/{1}/service/Impl/{2}ServiceImpl.java", module, javaSrcPath, className);
            //Sync Data
            String syncDataConsumerPath = MessageFormat.format("/{0}-service/{1}/sync/kafka/consumer/Sync{2}Consumer.java", module, javaSrcPath, className);
            String syncDataVOPath = MessageFormat.format("/{0}-service/{1}/sync/kafka/vo/Sync{2}VO.java", module, javaSrcPath, className);
//
//            String webPath = MessageFormat.format("{0}{1}-generate/{2}", projectPath, moduleSplit[0], CodeResourceUtil.web_root_package);
//
//            String webJsLangZh = MessageFormat.format("/locales/zh-CN/{0}_lang.js", lowerName);
//            String webJsLangEn = MessageFormat.format("/locales/en-US/{0}_lang.js", lowerName);
//            String webPageListPath = MessageFormat.format("/views/{0}/{1}_list.vue", lowerName, lowerName);
//            String webPageEditPath = MessageFormat.format("/views/{0}/{1}_edit.vue", lowerName, lowerName);
//            String webPageDetailPath = MessageFormat.format("/views/{0}/{1}_detail.vue", lowerName, lowerName);
//
//            CommonPageParser.WriterPage(context, "/template/ant-vue/js/LangZh.ftv", webPath, webJsLangZh);
//            CommonPageParser.WriterPage(context, "/template/ant-vue/js/LangEn.ftv", webPath, webJsLangEn);
//            CommonPageParser.WriterPage(context, "/template/ant-vue/page/ListTemplate.ftv", webPath, webPageListPath);
//            CommonPageParser.WriterPage(context, "/template/ant-vue/page/EditTemplate.ftv", webPath, webPageEditPath);
//            CommonPageParser.WriterPage(context, "/template/ant-vue/page/DetailTemplate.ftv", webPath, webPageDetailPath);


           // CommonPageParser.WriterPage(context, "/template/EntityParentTemplate.ftj", javaPath, javaEntityParentPath);
            CommonPageParser.WriterPage(context, "/template/EntityTemplate.ftj", javaPath, javaEntityPath);
            CommonPageParser.WriterPage(context, "/template/ListVoTemplate.ftj", javaPath, javaListVoPath);
            CommonPageParser.WriterPage(context, "/template/EntityVoTemplate.ftj", javaPath, javaVOPath);
            CommonPageParser.WriterPage(context, "/template/ReqListTemplate.ftj", javaPath, javaListReqPath);
            CommonPageParser.WriterPage(context, "/template/ReqModifyTemplate.ftj", javaPath, javaModifyPath);
            CommonPageParser.WriterPage(context, "/template/ReqDeleteTemplate.ftj", javaPath, javaDeletePath);
            CommonPageParser.WriterPage(context, "/template/MapperTemplate.ftj", javaPath, javaMapperPath);
            CommonPageParser.WriterPage(context, "/template/MapperTemplate.ftx", javaPath, javaMapperXmlPath);
            if (serviceControllerFlag) {
                CommonPageParser.WriterPage(context, "/template/ControllerTemplate.ftj", javaPath, javaControllerPath);
                CommonPageParser.WriterPage(context, "/template/ServiceTemplate.ftj", javaPath, javaServicePath);
                CommonPageParser.WriterPage(context, "/template/ServiceImplTemplate.ftj", javaPath, javaServiceImplPath);
            }

            // // ES
//             CommonPageParser.WriterPage(context, "/template/es/EntityParentTemplate.ftj", javaPath, javaEsEntityParentPath);
//             CommonPageParser.WriterPage(context, "/template/es/ServiceTemplate.ftj", javaPath, javaEsServicePath);
//             CommonPageParser.WriterPage(context, "/template/es/ServiceImplTemplate.ftj", javaPath, javaEsServiceImplPath);
//             CommonPageParser.WriterPage(context, "/template/es/RepositoryTemplate.ftj", javaPath, javaEsRepositoryPath);
            //
            // //Sync Data
            // CommonPageParser.WriterPage(context, "/template/sync/SyncDataConsumerTemplate.ftj", javaPath, syncDataConsumerPath);
            // CommonPageParser.WriterPage(context, "/template/sync/SyncDataVOTemplate.ftj", javaPath, syncDataVOPath);

        } catch (Exception e) {
            log.error("********** 表: " + tableName + " 构建异常 *********", e);
        }

    }

    private static String getProjectPath() {
        String path = CodeGenerateFactory.class.getResource("/").getPath().replace("/target/classes/", "");
        path = path.substring(0, path.lastIndexOf("/"));
        return path + "/";
    }
}
