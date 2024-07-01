package com.mk.utils;

import org.springframework.validation.BindingResult;

import java.util.Objects;

public class ValidateParamUtil {
    public static String validateFn (BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        }
        return null;
    }
}
