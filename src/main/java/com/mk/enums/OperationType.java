package com.mk.enums;

public enum OperationType {
    ADD("添加"),
    UPDATE("修改"),
    DELETE("删除");

    private final String desc;

    OperationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
