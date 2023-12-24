package com.ppf.oj.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求（管理员专用）
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 标签列表（json 数组）
     */
    @TableField(value = "tags")
    private List<String> tags;

    /**
     * 题目答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 判题用例（json 数组）
     */
    @TableField(value = "judgeCase")
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json 对象）
     */
    @TableField(value = "judgeConfig")
    private JudgeConfig judgeConfig;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}