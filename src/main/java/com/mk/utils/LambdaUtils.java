package com.mk.utils;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class LambdaUtils {

    @FunctionalInterface
    public interface SFunction<T, R> extends Serializable {
        R apply(T t);
    }

    public static <T> String getFieldName(SFunction<T, ?> fn) {
        try {
            // 从函数接口中获取序列化的Lambda
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            // 从SerializedLambda中获取方法名
            String methodName = serializedLambda.getImplMethodName();
            if (methodName.startsWith("get")) {
                methodName = methodName.substring(3);
            } else if (methodName.startsWith("is")) {
                methodName = methodName.substring(2);
            }
            // 将方法名转换为属性名（首字母小写）
            return Introspector.decapitalize(methodName);
        } catch (Exception e) {
            throw new RuntimeException("解析Lambda表达式失败", e);
        }
    }
}
