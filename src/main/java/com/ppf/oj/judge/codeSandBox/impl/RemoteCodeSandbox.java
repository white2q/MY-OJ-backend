package com.ppf.oj.judge.codeSandBox.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.ppf.oj.common.ErrorCode;
import com.ppf.oj.exception.BusinessException;
import com.ppf.oj.judge.codeSandBox.CodeSandbox;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ppf.oj.judge.codeSandBox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;

public class RemoteCodeSandbox implements CodeSandbox {


//    private static final String REMOTE_CODE_SANDBOX_URL = "http://127.0.0.1:8010/run/code";

//    private static final String REMOTE_CODE_SANDBOX_URL = "http://192.168.232.130:8010/run/code";
//    private static final String REMOTE_CODE_SANDBOX_URL = "http://47.108.114.160:8010/run/code";

    private static final String REMOTE_CODE_SANDBOX_URL = "http://162.14.107.142:8010/run/code";

    @Override
    public ExecuteCodeResponse codeRunning(ExecuteCodeRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();
        List<String> inputList = request.getInputList();

        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("language", language);
        params.put("inputList", inputList);

        String response = HttpRequest.post(REMOTE_CODE_SANDBOX_URL)
                .body(JSONUtil.toJsonStr(params))
                .execute()
                .body();

        if (StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "executeCode remoteSandbox error, message = " + response);
        }

        ExecuteCodeResponse executeCodeResponse = JSONUtil.toBean(response, ExecuteCodeResponse.class);

        System.out.println(executeCodeResponse);
        return executeCodeResponse;
    }
}
