package com.ppf.oj.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.ppf.oj.common.BaseResponse;
import com.ppf.oj.common.ErrorCode;
import com.ppf.oj.common.ResultUtils;
import com.ppf.oj.exception.BusinessException;
import com.ppf.oj.exception.ThrowUtils;
import com.ppf.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.ppf.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.ppf.oj.model.dto.questionsubmit.UserCodeSaveRequest;
import com.ppf.oj.model.dto.questionsubmit.UserHistoryCodeRequest;
import com.ppf.oj.model.entity.QuestionSubmit;
import com.ppf.oj.model.entity.User;
import com.ppf.oj.model.enums.QuestionSubmitStatusEnum;
import com.ppf.oj.model.vo.QuestionSubmitVO;
import com.ppf.oj.model.vo.UserCodeSaveResponse;
import com.ppf.oj.rabbitmq.MessageProducer;
import com.ppf.oj.service.QuestionSubmitService;
import com.ppf.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codeSandbox.type}")
    private String key;

    @Resource
    private UserService userService;

    @Resource
    private MessageProducer messageProducer;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String USER_CODE_SAVE_KEY = "USER_CODE_SAVE_KEY";

    private final static Gson GSON = new Gson();

    /**
     * 保存用户代码到Redis中， 过期时间为1周
     *
     * @param userCodeSaveRequest
     */
    @PostMapping("/save")
    public void saveUserCode(@RequestBody UserCodeSaveRequest userCodeSaveRequest) {
        String code = userCodeSaveRequest.getCode();
        String language = userCodeSaveRequest.getLanguage();
        String questionId = userCodeSaveRequest.getQuestionId();
        String userId = userCodeSaveRequest.getUserId();
        String key = userId + questionId + language;
        // 校验参数
        ThrowUtils.throwIf(!StrUtil.isAllNotBlank(code, language, questionId, userId), ErrorCode.PARAMS_ERROR);
        try {
            redisTemplate.opsForValue().set(key, code, Duration.ofDays(7));
        } catch (Exception e) {
            log.error("保存用户代码到Redis中失败， 错误信息：{}", e.getMessage());
        }
    }

    /**
     * 从Redis中取用户之前提交的代码
     *
     * @param userHistoryCodeRequest
     */
    @PostMapping("/history")
    public BaseResponse<UserCodeSaveResponse> getUserSaveCode(@RequestBody UserHistoryCodeRequest userHistoryCodeRequest) {
        String language = userHistoryCodeRequest.getLanguage();
        String questionId = userHistoryCodeRequest.getQuestionId();
        String userId = userHistoryCodeRequest.getUserId();
        String key = userId + questionId + language;
        // 校验参数
        ThrowUtils.throwIf(!StrUtil.isAllNotBlank(language, questionId, userId), ErrorCode.PARAMS_ERROR);
        // 查看是否已存在键，存在则获取value-code返回，不存在则返回null
        if (redisTemplate.hasKey(key) == null) return null;
        String code = (String) redisTemplate.opsForValue().get(key);
        return ResultUtils.success(new UserCodeSaveResponse(code,language));
    }

    @PostMapping("/add")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        if (questionId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        // 只允许登录的用户提交题目
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);
        // 手动深拷贝转JSON
        questionSubmit.setJudgeInfo(GSON.toJson(questionSubmitAddRequest.getJudgeInfo()));
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        boolean result = questionSubmitService.save(questionSubmit);
        ThrowUtils.throwIf(!result, new BusinessException(ErrorCode.OPERATION_ERROR));
        Long id = questionSubmit.getId();
        // 消息队列解耦
        messageProducer.sendMessage("code_exchange", id.toString(), key);
        return ResultUtils.success(id);
    }

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long pageSize = questionSubmitQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, pageSize),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, request));
    }
}
