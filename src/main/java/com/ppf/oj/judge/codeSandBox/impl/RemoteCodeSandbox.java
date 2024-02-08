package com.ppf.oj.judge.codeSandBox.impl;

import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;

public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        System.out.println("远程调用结果");
        return null;
    }
}
