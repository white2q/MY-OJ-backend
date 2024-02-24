package com.ppf.oj.model.enums;


public enum QuestionSubmitStatusEnum {
    WAITING("等待中", 0),
    RUNNING("运行中", 1),
    SUCCESS("完成", 2);
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

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }


}
