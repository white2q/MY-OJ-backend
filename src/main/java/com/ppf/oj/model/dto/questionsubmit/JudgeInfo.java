package com.ppf.oj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 判题信息
 *
 * @author panpengfei
 * @date 2023/12/23
 */
@Data
public class JudgeInfo {
    /**
     * 判题信息
     */
    private String message;
    /**
     * 运行时长
     */
    private String time;
    /**
     * 占用内存
     */
    private String memory;
}
