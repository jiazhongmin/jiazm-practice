package com.jiazm.practice;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiazm.practice.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Utils - JSON
 */
@Slf4j
public final class JsonUtils {

    /**
     * ObjectMapper
     */
//    private static ObjectMapper mapper;
    private static final ObjectMapper mapper = SpringUtils.getBean(ObjectMapper.class);

//    static {
//        mapper = new ObjectMapper()
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                // .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
//                .registerModules(new JavaTimeModule())
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//    }

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value 对象
     * @return JSOn字符串
     */
    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error("JsonUtils toJson Error", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json      JSON字符串
     * @param valueType 对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json,"");
        Assert.notNull(valueType,"");
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("JsonUtils toObject Error", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json          JSON字符串
     * @param typeReference 对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        Assert.hasText(json,"");
        Assert.notNull(typeReference,"");
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("JsonUtils toObject Error", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json     JSON字符串
     * @param javaType 对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json,"");
        Assert.notNull(javaType,"");
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("JsonUtils toObject Error", e);
        }
        return null;
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            log.error("JsonUtils writeValue Error", e);
        } catch (JsonMappingException e) {
            log.error("JsonUtils writeValue Error", e);
        } catch (IOException e) {
            log.error("JsonUtils writeValue Error", e);
        }
    }

    /**
     * 将对象转换为JSON流
     *
     * @param outputStream 流
     * @param value        对象
     */
    public static void writeValue(OutputStream outputStream, Object value) {
        try {
            mapper.writeValue(outputStream, value);
        } catch (JsonGenerationException e) {
            log.error("JsonUtils writeValue Error", e);
        } catch (JsonMappingException e) {
            log.error("JsonUtils writeValue Error", e);
        } catch (IOException e) {
            log.error("JsonUtils writeValue Error", e);
        }
    }

}