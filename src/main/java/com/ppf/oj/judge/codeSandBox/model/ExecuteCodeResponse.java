package com.ppf.oj.judge.codeSandBox.model;

import com.ppf.oj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;
    private String message;
    private JudgeInfo judgeInfo;
}
