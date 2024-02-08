package com.ppf.oj.judge.codeSandBox;

import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse codeRunning(ExecuteCodeRequest request);
}
