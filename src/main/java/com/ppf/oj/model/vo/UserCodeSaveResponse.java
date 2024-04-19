package com.ppf.oj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户历史代码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCodeSaveResponse {
    private String code;
    private String language;
}
