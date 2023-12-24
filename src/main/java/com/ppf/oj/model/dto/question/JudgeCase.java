package com.ppf.oj.model.dto.question;

import lombok.Data;

/**
 * 判题用例
 *
 * @author panpengfei
 * @date 2023/12/21
 */
@Data
public class JudgeCase {
    // 输入用例
    private String input;
    // 输出用例
    private String output;
}


