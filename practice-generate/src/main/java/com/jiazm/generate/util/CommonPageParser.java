package com.jiazm.generate.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class CommonPageParser {

    private static final Logger log = LoggerFactory.getLogger(CommonPageParser.class);
    private static VelocityEngine ve;
    private static boolean isReplace = true;

    static {
        try {
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "class");
            properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // String templateBasePath = CommonPageParser.class.getResource("/").getPath();
            // properties.setProperty("resource.loader", "file");
            // properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
            // properties.setProperty("file.resource.loader.path", templateBasePath);//
            // properties.setProperty("file.resource.loader.cache", "true");
            // properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
            properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
            properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
            properties.setProperty("directive.set.null.allowed", "true");
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            ve = velocityEngine;
        } catch (Exception e) {
            log.error("propertie init error",e);
        }
    }

    public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {
        try {
            File file = new File(fileDirPath + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else if (isReplace) {
                log.info("替换文件:" + file.getAbsolutePath());
            }

            Template template = ve.getTemplate(templateName, "UTF-8");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
            log.info("生成文件：" + file.getAbsolutePath());
        } catch (Exception e) {
            log.error("",e);
        }
    }
}
