package com.ppf.oj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ppf.oj.model.dto.question.JudgeConfig;
import com.ppf.oj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 创建用户 id
     */
    @TableField(value = "userId")
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

    /**
     * 题目提交数
     */
    @TableField(value = "submitNum")
    private Integer submitNum;

    /**
     * 题目通过数
     */
    @TableField(value = "acceptedNum")
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    @TableField(value = "judgeConfig")
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    @TableField(value = "thumbNum")
    private Integer thumbNum;

    /**
     * 收藏数
     */
    @TableField(value = "favourNum")
    private Integer favourNum;


    /**
     * 创建人信息
     */
    private UserVO userVO;

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

    /**
     * 包装类转对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();

        List<String> tags = questionVO.getTags();
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();

        BeanUtils.copyProperties(questionVO, question);

        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }

        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }

        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        questionVO.setTags(JSONUtil.toList(question.getTags(), String.class));
        return questionVO;
    }
}
