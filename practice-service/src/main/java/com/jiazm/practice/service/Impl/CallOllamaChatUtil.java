package com.jiazm.practice.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author jiazhongmin
 * @Date 2025/2/10 16:03
 **/
@Service
public class CallOllamaChatUtil {
    @Resource
    private OllamaChatModel ollamaChatModel;

    public String callDeepseek(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        //ChatResponse chatResponse = ollamaChatModel.call(prompt);
        CompletableFuture.supplyAsync(() -> ollamaChatModel.call(prompt))
                .thenAccept(response -> {
                    // 逐字输出响应
                    for (char c : response.getResult().getOutput().getContent().toCharArray()) {
                        System.out.print(c);
                        try {
                            // 控制输出速度，这里使用50毫秒间隔
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(); // 换行
                })
                .exceptionally(ex -> {
                    System.err.println("Error occurred: " + ex.getMessage());
                    return null;
                });
        return "";
    }
}
