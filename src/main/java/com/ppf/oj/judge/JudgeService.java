package com.ppf.oj.judge;

import com.ppf.oj.model.dto.questionsubmit.JudgeInfo;
import com.ppf.oj.model.entity.QuestionSubmit;

public interface JudgeService {
    JudgeInfo doJudge(long questionSubmitId);
}
