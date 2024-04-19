package com.ppf.oj.judge.strategy.impl;

import com.ppf.oj.judge.codeSandBox.model.JudgeInfo;
import com.ppf.oj.judge.strategy.JudgeStrategy;
import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.model.dto.question.JudgeConfig;
import com.ppf.oj.model.enums.QuestionSubmitStatusEnum;
import com.ppf.oj.model.vo.QuestionSubmitAddResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JavaLanguageJudgeStrategy implements JudgeStrategy {

    private static final Long SD2MS = 1000 * 1000L;
    private static final Long MB2B = 1024 * 1024L;

    @Override
    public QuestionSubmitAddResponse doJudge(JudgeContext judgeContext) {
        QuestionSubmitAddResponse questionSubmitAddResponse = new QuestionSubmitAddResponse();
        List<String> outputList_raw = judgeContext.getOutputList_raw();
        List<String> outputList_user = judgeContext.getOutputList_user();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        JudgeConfig judgeConfig = judgeContext.getJudgeConfig();

        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if (outputList_raw.size() != outputList_user.size()) {
            questionSubmitAddResponse.setStatus(QuestionSubmitStatusEnum.SYSTEM_ERROR.getText());
            return questionSubmitAddResponse;
        }

        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < outputList_raw.size(); i++) {
            if (!outputList_raw.get(i).equals(outputList_user.get(i))) {
                questionSubmitAddResponse.setStatus(QuestionSubmitStatusEnum.WRONG_ANSWER.getText());
                return questionSubmitAddResponse;
            }
        }

        // 判断题目限制
        long time = judgeInfo.getTime();
        long memory = judgeInfo.getMemory();

        long timeLimit = judgeConfig.getTimeLimit() * SD2MS;
        long memoryLimit = judgeConfig.getMemoryLimit() * MB2B;

        // todo 由于Java输入输出的原因，应该适当降低用户运行时长限制（要不就不改，让用户自己通过缓冲流输入输出）
        if (time - 10L >= timeLimit) {
            questionSubmitAddResponse.setStatus(QuestionSubmitStatusEnum.TIME_LIMIT_EXCEEDED.getText());
            return questionSubmitAddResponse;
        }

        if (memory >= memoryLimit) {
            questionSubmitAddResponse.setStatus(QuestionSubmitStatusEnum.OUT_OF_MEMORY.getText());
            return questionSubmitAddResponse;
        }

        questionSubmitAddResponse.setStatus(QuestionSubmitStatusEnum.ACCEPTED.getText());
        questionSubmitAddResponse.setMessage(outputList_user.toString());
        questionSubmitAddResponse.setMemory(memory);
        questionSubmitAddResponse.setTime(time);
        return questionSubmitAddResponse;
    }
}
