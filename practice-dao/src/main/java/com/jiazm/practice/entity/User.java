package com.jiazm.practice.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author jiazhongmin
 * @date 2023/8/23
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private LocalDateTime createTime;
}
