package com.mk.handler;


import com.mk.constant.MessageErrorConstant;
import com.mk.exception.BaseException;
import com.mk.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg=username + "已存在,不能重复";
            return Result.error(msg);
        }else {
            return Result.error(MessageErrorConstant.UNKNOWN_ERROR);
        }
    }


    /**
     * 处理 @RequestBody 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException异常信息：{}", e.getMessage());
        // 获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        List<ObjectError> errors = bindingResult.getAllErrors();
        // 拼接错误信息
        StringBuilder message = new StringBuilder();
        for (ObjectError error : errors) {
            message.append(error.getDefaultMessage()).append(",");
        }

        return Result.error(message.substring(0,message.length()-1));
    }

    /**
     * 处理 @Validated 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException异常信息：{}", e.getMessage());
        // 使用 LinkedHashSet 存储校验信息，保证顺序
        LinkedHashSet<ConstraintViolation<?>> violations = new LinkedHashSet<>(e.getConstraintViolations());
        log.error(violations.toString());
        // 获取第一个错误信息
        String errorMessage = violations.stream()
                .findFirst() // 获取第一个元素
                .map(ConstraintViolation::getMessage)
                .orElse("参数校验失败"); // 如果没有错误信息，则使用默认值
        return Result.error(errorMessage);
    }


    /**
     * 处理 @ModelAttribute 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleBindException(BindException e) {
        e.printStackTrace();
        log.error("handleBindException异常信息：{}", e.getMessage());
        // 获取所有校验失败的字段和错误信息
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        // 拼接错误信息
        StringBuilder message = new StringBuilder();
        for (ObjectError error : errors) {
            message.append(error.getDefaultMessage()).append(",");
        }
        return Result.error(message.substring(0, message.length() - 1));
    }
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(StringUtils.hasLength(ex.getMessage())?ex.getMessage():"服务异常！");
    }


}
