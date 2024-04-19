package com.ppf.oj.judge.codeSandBox;

import com.ppf.oj.judge.codeSandBox.impl.RemoteCodeSandbox;

public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
//        switch (type) {
//            case "Remote" :
//                return new RemoteCodeSandbox();
//            case "ThirdParty" :
//                return new ThirdPartyCodeSandbox();
//            default:
//                return new ExampleCodeSandbox();
//        }
        return new RemoteCodeSandbox();
    }
}
