package com.ppf.oj.judge.codeSandBox.impl;

import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;

public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        System.out.println("示例运行结果");
        return null;
    }
}
