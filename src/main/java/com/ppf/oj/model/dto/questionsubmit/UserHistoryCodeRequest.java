package com.ppf.oj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 用户历史代码
 */
@Data
public class UserHistoryCodeRequest {
    private String language;
    private String questionId;
    private String userId;
}
