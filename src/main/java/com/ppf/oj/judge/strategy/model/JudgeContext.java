package com.ppf.oj.judge.strategy.model;

import com.ppf.oj.model.dto.question.JudgeConfig;
import com.ppf.oj.judge.codeSandBox.model.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JudgeContext {
    private List<String> outputList_raw;
    private List<String> outputList_user;
    private JudgeInfo judgeInfo;
    private JudgeConfig judgeConfig;
    private String language;
}
