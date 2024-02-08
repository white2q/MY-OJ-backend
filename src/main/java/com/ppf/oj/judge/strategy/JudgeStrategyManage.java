package com.ppf.oj.judge.strategy;

import com.ppf.oj.judge.strategy.impl.DefaultJudgeStrategy;
import com.ppf.oj.judge.strategy.impl.JavaLanguageJudgeStrategy;
import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.model.dto.questionsubmit.JudgeInfo;
import org.springframework.stereotype.Service;

@Service
public class JudgeStrategyManage {
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("Java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
