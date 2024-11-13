package com.jiazm.practice.config;
//import com.sun.speech.freetts
/**
 * @author jiazhongmin
 * @Date 2024/5/29 14:56
 **/
public class Automatic {
    /**
     * 语音转文字并播放
     *
     * @param text 要播放的文字
     * @throws Exception 异常捕捉
     */
    public static void speak(String text) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        speak("打开卧室灯泡");
        System.out.println("生成成功！");
    }
}
