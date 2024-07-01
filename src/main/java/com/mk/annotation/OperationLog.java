package com.mk.annotation;

import com.mk.enums.OperationType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperationLog {
    String module() default ""; // 操作模块，例如 "员工管理"，"商品管理"
    OperationType operation(); // 操作类型，例如 "添加"，"修改"，"删除"
}
