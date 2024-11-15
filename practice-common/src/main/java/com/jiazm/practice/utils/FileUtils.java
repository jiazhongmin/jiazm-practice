package com.jiazm.practice.utils;

import com.jiazm.practice.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author jiazm3
 * @date 2021/12/22 15:52
 */
@Slf4j
public class FileUtils {
    /**
     * 从inputStream中读取字节数组
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int len;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.info("读取inputStream异常", e);
            throw new BaseException(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.info("关闭输入流异常", e);
            }
        }
    }

    /**
     * 从inputStream中读取字节数组
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] notCloseReadStream(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int len;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.info("读取inputStream异常", e);
            throw new BaseException(e.getMessage());
        }
    }

    /**
     * 获取文件类型
     *
     * @param file
     */
    public static String getFileType(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }


    /**
     * 文件大小保留三位小数
     *
     * @param len
     * @param number
     * @param fileUnit
     * @return
     */
    public static String getFileSizeStr(Long len, Integer number, String fileUnit) {
        BigDecimal bg = new BigDecimal(len);
        return bg.divide(new BigDecimal(number), 3, RoundingMode.HALF_UP) + fileUnit;
    }

    public static String getNotSuffixFileSize(MultipartFile file, String unit) {
        long len = file.getSize();
        String fileSize = "";
        if ("B".equalsIgnoreCase(unit)) {
            fileSize = getFileSize(len, 1);
        } else if ("K".equalsIgnoreCase(unit)) {
            fileSize = getFileSize(len, 1024);
        } else if ("M".equalsIgnoreCase(unit)) {
            fileSize = getFileSize(len, 1048576);
        } else if ("G".equalsIgnoreCase(unit)) {
            fileSize = getFileSize(len, 1073741824);
        }
        return fileSize;
    }

    /**
     * 文件大小保留三位小数
     *
     * @param len
     * @param number
     * @return
     */
    public static String getFileSize(Long len, Integer number) {
        BigDecimal bg = new BigDecimal(len);
        return bg.divide(new BigDecimal(number), 3, RoundingMode.HALF_UP).toString();
    }

    /**
     * 判断文件大小
     *
     * @param file 文件长度
     * @param size 限制大小
     * @param unit 限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(MultipartFile file, int size, String unit) {
        long len = file.getSize();
        double fileSize = 0;
        if ("B".equalsIgnoreCase(unit)) {
            fileSize = (double) len;
        } else if ("K".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1024;
        } else if ("M".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1048576;
        } else if ("G".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    /**
     * 流转换文件
     *
     * @param fileContent
     * @param file
     * @throws IOException
     */
    public static File copyInputStreamToFile(byte[] fileContent, File file) {
        InputStream inputStream = new ByteArrayInputStream(fileContent);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            throw new BaseException("-999", e.getMessage());
        }
        return file;
    }


    /**
     * 深拷贝 inputStream
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static ByteArrayOutputStream beenCopyInputStream(InputStream inputStream) throws Exception {
        // 读取输入流中的所有数据到字节数组
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        // 创建字节数组的副本作为深拷贝
        return output;
    }
}
