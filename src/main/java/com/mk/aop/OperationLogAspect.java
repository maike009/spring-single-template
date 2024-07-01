package com.mk.aop;


import cn.hutool.json.JSONUtil;
import com.mk.annotation.OperationLog;
import com.mk.context.BaseContext;
import com.mk.module.service.IOperateLogService;
import com.mk.pojo.entity.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component
@Aspect//切面类
public class OperationLogAspect {


    private final IOperateLogService operateLogService;

    public OperationLogAspect(IOperateLogService operateLogService) {
        this.operateLogService = operateLogService;
    }


    @Around("@annotation(com.mk.annotation.OperationLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取目标方法
        Method method = methodSignature.getMethod();
        // 获取 OperationLog 注解
        OperationLog operationLog = method.getAnnotation(OperationLog.class);

        // 记录日志开始时间
        Long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 记录日志结束时间
        Long endTime = System.currentTimeMillis();

        // 计算方法执行耗时
        Long costTime = endTime - startTime;

        // 获取操作信息,如修改的是什么（员工，或产品）
        String module = operationLog.module();
        // 操作类型（增删改）
        String operation = operationLog.operation().getDesc();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = args != null ? Arrays.toString(args) : "方法参数为空";
        // 获取返回值
        String returnValue = result != null ? JSONUtil.toJsonStr(result) : "返回结果为空";

        // 保存操作日志
        OperateLog log = new OperateLog()
                .setOperateUser(BaseContext.getCurrentId())
                .setOperateType(operation)
                .setOperateName(module)
                .setMethodParams(methodParams)
                .setReturnValue(returnValue)
                .setCostTime(costTime);
        operateLogService.save(log);

        // 返回目标方法的返回值
        return result;

    }
}
