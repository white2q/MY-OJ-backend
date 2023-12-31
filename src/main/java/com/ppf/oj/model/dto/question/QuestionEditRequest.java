package com.ppf.oj.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑请求（用户专用）
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}