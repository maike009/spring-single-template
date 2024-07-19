package com.mk.pojo.dto;

import lombok.Data;

@Data
public class UserDto extends PageParamDto{

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;
}
