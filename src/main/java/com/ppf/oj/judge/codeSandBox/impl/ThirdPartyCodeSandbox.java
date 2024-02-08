package com.ppf.oj.judge.codeSandBox.impl;

import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        System.out.println("第三方调用结果");
        return null;
    }
}
