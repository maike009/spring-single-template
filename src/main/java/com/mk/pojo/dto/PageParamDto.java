package com.mk.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageParamDto {
    @ApiModelProperty("页码")
    @NotNull(message = "页码不能为空")
    public Integer page;
    @ApiModelProperty("每页最大数")
    @NotNull(message = "每页最大数不能为空")
    public Integer pageSize;
//    public PageParamDto(){
//        this.page=1;
//        this.pageSize=10;
//    }
}
