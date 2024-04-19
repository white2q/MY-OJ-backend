package com.ppf.oj.judge.codeSandBox.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.ppf.oj.common.ErrorCode;
import com.ppf.oj.exception.BusinessException;
import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;

public class RemoteCodeSandbox implements CodeSandbox {

    @Value("${codeSandbox.url}")
    private String url;

    @Value("${codeSandbox.port}")
    private String port;

    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        String codeSandboxUrl = String.format("http://%s:%s/run/code", url, port);
        String code = request.getCode();
        String language = request.getLanguage();
        List<String> inputList = request.getInputList();

        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("language", language);
        params.put("inputList", inputList);

        String response = HttpRequest.post(codeSandboxUrl)
                .body(JSONUtil.toJsonStr(params))
                .execute()
                .body();

        if (StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "executeCode remoteSandbox error, message = " + response);
        }
        return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
