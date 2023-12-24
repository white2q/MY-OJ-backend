package com.ppf.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppf.oj.model.dto.question.QuestionQueryRequest;
import com.ppf.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.ppf.oj.model.entity.Question;
import com.ppf.oj.model.entity.QuestionSubmit;
import com.ppf.oj.model.vo.QuestionSubmitVO;
import com.ppf.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 25137
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-21 18:59:58
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 分页获取帖子封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);

}
