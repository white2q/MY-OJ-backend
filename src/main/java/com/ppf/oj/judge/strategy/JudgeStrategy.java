package com.ppf.oj.judge.strategy;

import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.judge.codeSandBox.model.JudgeInfo;
import com.ppf.oj.model.vo.QuestionSubmitAddResponse;

public interface JudgeStrategy {
    QuestionSubmitAddResponse doJudge(JudgeContext judgeContext);
}
