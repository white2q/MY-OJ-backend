package com.ppf.oj.model.enums;


public enum QuestionSubmitStatusEnum {

    WAITING("等待中", 0),
    RUNNING("运行中", 1),
    SUCCESS("完成", 2),

    ACCEPTED("AC", 20000),

    WRONG_ANSWER("WR", 30000),

    TIME_LIMIT_EXCEEDED("TLE", 50100),

    SYSTEM_ERROR("SE", 50000),

    OUT_OF_MEMORY("OOM", 50200);


    private final String text;
    private final int value;

    QuestionSubmitStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public static QuestionSubmitStatusEnum getEnumByValue(int value) {
        for (QuestionSubmitStatusEnum anEnum : QuestionSubmitStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    public static Integer getEnumByText(String text) {
        for (QuestionSubmitStatusEnum e : QuestionSubmitStatusEnum.values()) {
            if (e.text.equals(text)) {
                return e.value;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }


}
