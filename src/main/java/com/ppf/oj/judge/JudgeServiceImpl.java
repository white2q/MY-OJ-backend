package com.ppf.oj.judge;

import cn.hutool.json.JSONUtil;
import com.ppf.oj.common.ErrorCode;
import com.ppf.oj.exception.BusinessException;
import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.CodeSandboxFactory;
import com.ppf.oj.judge.codeSandBox.CodeSandboxProxy;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.ppf.oj.judge.codeSandBox.model.JudgeInfo;
import com.ppf.oj.judge.strategy.JudgeStrategyManage;
import com.ppf.oj.judge.strategy.model.JudgeContext;
import com.ppf.oj.model.dto.question.JudgeCase;
import com.ppf.oj.model.dto.question.JudgeConfig;
import com.ppf.oj.model.entity.Question;
import com.ppf.oj.model.entity.QuestionSubmit;
import com.ppf.oj.model.enums.QuestionSubmitStatusEnum;
import com.ppf.oj.model.vo.QuestionSubmitAddResponse;
import com.ppf.oj.service.QuestionService;
import com.ppf.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codeSandbox.type}")
    private String type;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Override
    public QuestionSubmitAddResponse doJudge(long questionSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 2）如果题目提交状态不为等待中，就不用重复执行了
        Integer status = questionSubmit.getStatus();
        QuestionSubmitStatusEnum statusEnum = QuestionSubmitStatusEnum.getEnumByValue(status);
        if (!QuestionSubmitStatusEnum.WAITING.equals(statusEnum)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在运行中或已执行完");
        }

        // todo 加锁防止在还未改变状态之前反复向代码沙箱中提交代码，占用性能
        // 3）更改判题（题目提交）的状态为 “运行中”，防止重复执行
        if (!updateQuestionSubmitRunStatus(QuestionSubmitStatusEnum.RUNNING.getValue(), questionSubmitId)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4）调用沙箱，获取到执行结果
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String language = questionSubmit.getLanguage();
        String judgeConfig = question.getJudgeConfig();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .inputList(inputList)
                .language(language)
                .build();
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.codeRunning(executeCodeRequest);
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList_raw = judgeCases.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        List<String> outputList_user = executeCodeResponse.getOutputList();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setOutputList_raw(outputList_raw);
        judgeContext.setOutputList_user(outputList_user);
        // 实际运行中的信息（time， memory，message）
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setJudgeConfig(JSONUtil.toBean(judgeConfig, JudgeConfig.class));
        judgeContext.setLanguage(language);
        JudgeStrategyManage judgeStrategyManage = new JudgeStrategyManage();

        // 执行完代码沙箱， 将状态更改为success
        if (!updateQuestionSubmitRunStatus(QuestionSubmitStatusEnum.SUCCESS.getValue(), questionSubmitId)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmitAddResponse res = judgeStrategyManage.doJudge(judgeContext);

        String resStatus = res.getStatus();
        Integer st = QuestionSubmitStatusEnum.getEnumByText(resStatus);
        if (st != null) {
            if (!updateQuestionSubmitRunStatus(st, questionSubmitId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
            }
        }
        return res;
    }

    public boolean updateQuestionSubmitRunStatus(int status, long questionSubmitId) {
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setId(questionSubmitId);
        questionSubmit.setStatus(status);
        return questionSubmitService.updateById(questionSubmit);
    }
}
