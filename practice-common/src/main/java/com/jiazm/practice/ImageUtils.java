package com.jiazm.practice;

import com.jiazm.practice.exception.BaseException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

public final class ImageUtils {
    /**
     * 图片水印
     *
     * @param pressImg  水印图片
     * @param targetImg 目标图片
     * @param x         修正值 默认在中间
     * @param y         修正值 默认在中间
     * @param alpha     透明度
     */
    public final static void pressImage(String pressImg, String targetImg,
                                        int x, int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文字水印
     *
     * @param pressText 水印文字
     * @param targetImg 目标图片
     * @param fontName  字体名称
     * @param fontStyle 字体样式
     * @param color     字体颜色
     * @param fontSize  字体大小
     * @param x         修正值
     * @param y         修正值
     * @param alpha     透明度
     */
    public static void pressText(String pressText, String targetImg,
                                 String fontName, int fontStyle, Color color, int fontSize, int x,
                                 int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放
     *
     * @param filePath 图片路径
     * @param height   高度
     * @param width    宽度 (小于零则自动计算)
     * @param bb       比例不对时是否需要补白
     */
    public static String resize(String root_path, String filePath, int height,
                                int width, boolean bb) {
        String jpgfilePath = "";
        // String pngfilePath = "";
        try {
            double ratio = 0.0; // 缩放比例
            jpgfilePath = filePath.substring(0, filePath.lastIndexOf("."))
                    + "_a.jpg";
            // pngfilePath = filePath.substring(0, filePath.lastIndexOf("."))
            // + "_a.png";
            File f = new File(root_path + filePath);
            File jpgf = new File(root_path + jpgfilePath);
            // File pngf = new File(root_path + pngfilePath);
            BufferedImage bi = ImageIO.read(f);
            if (width <= 0) {
                width = bi.getWidth() * height / bi.getHeight();
            }
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);

            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (Integer.valueOf(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_USHORT_555_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }

            ImageIO.write((BufferedImage) itemp, "jpg", jpgf);
            // ImageIO.write((BufferedImage) itemp, "png", pngf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jpgfilePath;
    }

    /**
     * 缩放
     *
     * @param root_path
     * @param filePath  图片路径
     * @param height    高度
     * @param width     宽度 (小于零则自动计算)
     * @param bb        比例不对时是否需要补白
     * @param tail      文件名附加字段
     * @param picType   切后图片类型
     */
    public static String resize2(String root_path, String filePath, int height,
                                 int width, boolean bb, String tail, String picType) {
        String picfilePath = "";
        try {
            double ratio = 0.0; // 缩放比例
            picfilePath = filePath.substring(0, filePath.lastIndexOf("."))
                    + tail + "." + picType;
            File f = new File(root_path + filePath);
            File picf = new File(root_path + picfilePath);
            BufferedImage bi = ImageIO.read(f);
            if (width <= 0) {
                width = bi.getWidth() * height / bi.getHeight();
            }
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);

            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (Integer.valueOf(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            } else {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (Integer.valueOf(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_USHORT_555_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, picType, picf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picfilePath;
    }

    /**
     * 缩放
     *
     * @param filePath 图片路径
     * @param height   高度
     * @param width    宽度 (小于零则自动计算)
     * @param bb       比例不对时是否需要补白
     */
    public static String resizeBySize(String filePath, String fileName,
                                      int height, int width, boolean bb) {
//		String jpgfilePath = "";
        String resultFileName = "";
        try {
            double ratio = 0.0; // 缩放比例
//			jpgfilePath = filePath.substring(0, filePath.lastIndexOf("."))
//					+ "_" + height + "_" + width + ".jpg";
            resultFileName = fileName.substring(0, fileName.lastIndexOf("."))
                    + "_thumbnail" + ".png";
            File f = new File(filePath + fileName);
            //File jpgf = new File(root_path + jpgfilePath);
            File pngf = new File(filePath + resultFileName);
            BufferedImage bi = ImageIO.read(f);
            if (width <= 0) {
                width = bi.getWidth() * height / bi.getHeight();
            }
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);

            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (Integer.valueOf(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_USHORT_555_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }

            //ImageIO.write((BufferedImage) itemp, "jpg", jpgf);
            ImageIO.write((BufferedImage) itemp, "png", pngf);
        } catch (IOException e) {
            throw new BaseException("-999", "压缩文件失败:" + e.getMessage());
        }
        return resultFileName;
    }


    public static void main(String[] args) throws IOException {
        // pressImage("G:\\imgtest\\sy.jpg", "G:\\imgtest\\test1.jpg", 0, 0,
        // 0.5f);
        // pressText("我是文字水印", "G:\\imgtest\\test1.jpg", "黑体", 36, Color.white,
        // 80, 0, 0, 0.3f);
//		resize2("","C:\\Users\\qiaocan\\Desktop\\宏佳仁营业执照正本.png", 125,100,
//		 true,"_p", "png");
        File file = new File("C:\\Users\\qiaocan\\Desktop\\宏佳仁营业执照正本.png");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);
        inputStream.close();
        System.out.println();

    }

    public static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * 2014年1月3日10:52:49
     * 传入byte[],传出byte[]
     *
     * @param imageByte 图片字节数组
     * @param width     生成小图片宽度
     * @param height    生成小图片高度
     * @param gp        是否等比缩放
     * @return
     * @author liangpeng
     */
    public static byte[] compressPic(byte[] imageByte, int width, int height, boolean gp) {
        byte[] inByte = null;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
            Image img = ImageIO.read(byteInput);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                return inByte;
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (gp == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = width; // 输出的图片宽度
                    newHeight = height; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
                 * 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

                ImageWriter imgWrier;
                ImageWriteParam imgWriteParams;
                // 指定写图片的方式为 jpg
                imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
                imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
//	       // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT  
//	       imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);  
//	       // 这里指定压缩的程度，参数qality是取值0~1范围内，  
//	       imgWriteParams.setCompressionQuality((float)45217/imageByte.length);  
//	                          
//	       imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);  
//	       ColorModel colorModel = ColorModel.getRGBdefault();  
//	       // 指定压缩时使用的色彩模式  
//	       imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel  
//	               .createCompatibleSampleModel(100, 100)));  
                //将压缩后的图片返回字节流
                ByteArrayOutputStream out = new ByteArrayOutputStream(imageByte.length);
                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(tag, null, null), imgWriteParams);
                out.flush();
                out.close();
                byteInput.close();
                inByte = out.toByteArray();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return inByte;
    }

}
