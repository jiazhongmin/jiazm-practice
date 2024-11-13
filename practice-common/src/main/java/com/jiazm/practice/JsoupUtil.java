package com.jiazm.practice;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.safety.Whitelist;

/**
 * @author ：jiazm3
 * @date ：2023/11/07
 * @description：xxs工具类JsoupUtil
 */
@Slf4j
public class JsoupUtil {

    private JsoupUtil() {}

    /**
     * 标签白名单
     * relaxed() 允许的标签:
     *  a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4,
     *  h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。
     *  结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    private static final Whitelist WHITELIST = Whitelist.relaxed();

    /**
     * 设置自定义的标签和属性
     */
    static {
        /**
         * addTags() 设置白名单标签
         * addAttributes()  设置标签需要保留的属性 ,[:all]表示所有
         * preserveRelativeLinks()  是否保留元素的URL属性中的相对链接，或将它们转换为绝对链接,默认为false. 为false时将会把baseUri和元素的URL属性拼接起来
         */
        WHITELIST.preserveRelativeLinks(true);
    }

    public static String clean(String s) {
        /**
         * baseUri ,非空
         * 如果baseUri为空字符串或者不符合Http://xx类似的协议开头,属性中的URL链接将会被删除,如<a href='xxx'/>会变成<a/>
         * 如果WHITELIST.preserveRelativeLinks(false), 会将baseUri和属性中的URL链接进行拼接
         */
        return s.replaceAll("<","&lt").replaceAll(">","&gt");
    }

    /**
     * 处理Json类型的Html标签,进行xss过滤
     * @param s
     * @return
     */
    public static String cleanJson(String s) {
        //先处理双引号的问题
        return s.replaceAll("<","&lt").replaceAll(">","&gt");
    }

    /**
     * 将json字符串本身的双引号以外的双引号变成单引号
     * @param s
     * @return
     */
    @SuppressWarnings("squid:S3776")
    public static String jsonStringConvert(String s) {
        log.info("[处理JSON字符串] [将嵌套的双引号转成单引号] [原JSON] :{}",s);
        char[] temp = s.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        //如果该字符为双引号,下个字符不是逗号或大括号,替换
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            //将json字符串本身的双引号以外的双引号变成单引号
                            temp[j] = '\'';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        String r = new String(temp);
        log.info("[处理JSON字符串] [将嵌套的双引号转成单引号] [处理后的JSON] :{}",r);
        return r;
    }

}