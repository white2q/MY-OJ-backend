package com.ppf.oj.judge.codeSandBox;

import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        log.info("代码沙箱请求信息：" + request);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.codeRunning(request);
        log.info("代码沙箱响应信息：" + executeCodeResponse);
        return executeCodeResponse;
    }
}
