package com.ppf.oj.judge.strategy.impl;
import com.ppf.oj.model.dto.question.JudgeConfig;
import java.util.List;

import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.judge.strategy.JudgeStrategy;
import com.ppf.oj.model.dto.questionsubmit.JudgeInfo;
import com.ppf.oj.model.enums.QuestionSubmitStatusEnum;
import org.springframework.stereotype.Service;

@Service
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
     List<String> outputList_raw = judgeContext.getOutputList_raw();
     List<String> outputList_user = judgeContext.getOutputList_user();
     JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
     JudgeConfig judgeConfig = judgeContext.getJudgeConfig();

        JudgeInfo judgeInfoResponse = new JudgeInfo();

        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if(outputList_raw.size() != outputList_user.size()) {
            judgeInfoResponse.setMessage(QuestionSubmitStatusEnum.WRONG_ANSWER.getText());
            return judgeInfoResponse;
        }

        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < outputList_raw.size(); i++) {
            if(!outputList_raw.get(i).equals(outputList_user.get(i))) {
                judgeInfoResponse.setMessage(QuestionSubmitStatusEnum.WRONG_ANSWER.getText());
            }
        }

        // 判断题目限制
        long time = judgeInfo.getTime();
        long memory = judgeInfo.getMemory();

        long timeLimit = judgeConfig.getTimeLimit();
        long memoryLimit = judgeConfig.getMemoryLimit();

        if(time >= timeLimit) {
            judgeInfoResponse.setMessage(QuestionSubmitStatusEnum.WRONG_ANSWER.getText());
            return judgeInfoResponse;
        }

        if(memory >= memoryLimit) {
            judgeInfoResponse.setMessage(QuestionSubmitStatusEnum.WRONG_ANSWER.getText());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(QuestionSubmitStatusEnum.SUCCESS.getText());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        return judgeInfoResponse;
    }
}
