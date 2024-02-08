package com.ppf.oj.judge.strategy;

import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
