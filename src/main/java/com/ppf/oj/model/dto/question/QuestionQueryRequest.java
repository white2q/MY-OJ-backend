package com.ppf.oj.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ppf.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * 创建用户 id
     */
    private Long userId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}