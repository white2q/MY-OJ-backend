package com.ppf.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.ppf.oj.common.BaseResponse;
import com.ppf.oj.common.ErrorCode;
import com.ppf.oj.common.ResultUtils;
import com.ppf.oj.exception.BusinessException;
import com.ppf.oj.exception.ThrowUtils;
import com.ppf.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.ppf.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.ppf.oj.model.entity.QuestionSubmit;
import com.ppf.oj.model.entity.User;
import com.ppf.oj.model.vo.QuestionSubmitVO;
import com.ppf.oj.service.QuestionSubmitService;
import com.ppf.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    @PostMapping("/add")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);
        questionSubmit.setJudgeInfo(GSON.toJson(questionSubmitAddRequest.getJudgeInfo()));
        User loginUser = userService.getLoginUser(request);
        // 只允许登录的用户提交题目
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        questionSubmit.setUserId(loginUser.getId());
        boolean result = questionSubmitService.save(questionSubmit);
        ThrowUtils.throwIf(!result, new BusinessException(ErrorCode.OPERATION_ERROR));
        Long id = questionSubmit.getId();

        // TODO 调用判题服务
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
