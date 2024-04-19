package com.ppf.oj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 用户历史代码保存请求
 */
@Data
public class UserCodeSaveRequest {
    private String code;
    private String language;
    private String questionId;
    private String userId;
}
