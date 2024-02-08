package com.ppf.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppf.oj.model.dto.question.QuestionQueryRequest;
import com.ppf.oj.model.entity.Question;
import com.ppf.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 25137
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2023-12-21 18:59:49
 */
public interface QuestionService extends IService<Question> {


    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 分页获取帖子封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
