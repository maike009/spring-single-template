package com.mk.extract;

import com.mk.pojo.entity.OperateLog;

public interface OperationObjectExtractor {
    /**
     * 从方法参数中提取操作对象信息。
     * @param args 方法参数列表
     * @return 操作对象信息，包含 objectType, objectId, objectName
     */
    OperateLog extract(Object[] args);
}