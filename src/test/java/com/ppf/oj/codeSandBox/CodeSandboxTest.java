package com.ppf.oj.codeSandBox;

import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.CodeSandboxFactory;
import com.ppf.oj.judge.codeSandBox.CodeSandboxProxy;
import com.ppf.oj.judge.codeSandBox.impl.ExampleCodeSandbox;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codeSandbox.type}")
    private String type;

    @Test
    public void test() {
        CodeSandbox exampleCodeSandbox = new ExampleCodeSandbox();
        exampleCodeSandbox.codeRunning(null);
    }

    @Test
    public void testFactory() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox.codeRunning(null);
    }

    @Test
    public void  testProxy() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        codeSandboxProxy.codeRunning(null);
    }

}