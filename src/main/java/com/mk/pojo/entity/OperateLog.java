package com.mk.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author maike
 * @since 2024-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("operate_log")
@ApiModel(value="OperateLog对象", description="操作日志表")
public class OperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作人ID")
    private Long operateUser;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;

    @ApiModelProperty(value = "操作类型（增删改）")
    private String operateType;

    @ApiModelProperty(value = "操作的东西（员工，用户，产品等等）")
    private String operateName;

    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    @ApiModelProperty(value = "返回值")
    private String returnValue;

    @ApiModelProperty(value = "方法执行耗时, 单位:ms")
    private Long costTime;


}
