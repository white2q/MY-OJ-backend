package com.ppf.oj.judge;

import com.ppf.oj.model.vo.QuestionSubmitAddResponse;

public interface JudgeService {
    QuestionSubmitAddResponse doJudge(long questionSubmitId);
}
