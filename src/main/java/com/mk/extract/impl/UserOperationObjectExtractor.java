package com.mk.extract.impl;

import com.mk.extract.OperationObjectExtractor;
import com.mk.pojo.entity.OperateLog;
import com.mk.pojo.entity.User;

public class UserOperationObjectExtractor implements OperationObjectExtractor {
    @Override
    public OperateLog extract(Object[] args) {
        if (args != null && args.length > 0) {
            Object targetObject = args[0];
            if (targetObject instanceof User) {
                User user = (User) targetObject;
                return new OperateLog();
            }
        }
        return null;
    }
}
