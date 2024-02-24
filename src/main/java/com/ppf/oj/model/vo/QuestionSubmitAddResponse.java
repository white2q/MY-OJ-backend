package com.ppf.oj.model.vo;

import lombok.Data;

/**
 * 代码运行结果响应
 */
@Data
public class QuestionSubmitAddResponse {
    private String status;
    private String message;
    private Long time;
    private Long memory;
}
