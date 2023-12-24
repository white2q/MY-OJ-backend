package com.ppf.oj.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ppf.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    @TableField(value = "language")
    private String language;

    /**
     * 题目 id
     */
    @TableField(value = "questionId")
    private Long questionId;

    /**
     * 创建用户 id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}