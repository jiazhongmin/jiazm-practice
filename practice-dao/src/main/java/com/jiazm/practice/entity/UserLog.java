package com.jiazm.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author jiazhongmin
 * @date 2023/8/23
 */
@Data
public class UserLog {
    private Integer logId;
    private Integer userId;
    private String username;
    private Integer preLogId;
    private String question;
    private String answer;
    private LocalDateTime dateTime;
    private Long consumeTime;
}
