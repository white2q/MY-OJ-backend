package com.ppf.oj.model.dto.question;

import lombok.Data;

/**
 * 判题配置
 *
 * @author panpengfei
 * @date 2023/12/21
 */
@Data
public class JudgeConfig {
    // 时间限制
    private long timeLimit;
    // 内存限制
    private long memoryLimit;
}


