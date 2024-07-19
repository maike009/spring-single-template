package com.mk.pojo.dto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mk.utils.LambdaUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.mk.utils.LambdaUtils.getFieldName;

/**
 * 分页参数DTO，用于封装分页查询的参数。
 * 包含页码、每页查询数量、排序方式等信息。
 */
@Data
public class PageParamDto {
    @ApiModelProperty("页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    public Integer pageNo;
    @ApiModelProperty("每页查询数量")
    @NotNull(message = "每页查询数量不能为空")
    @Min(value = 1, message = "每页查询数量不能小于1")
    public Integer pageSize;

    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;
    @ApiModelProperty("排序方式")
    private String sortBy;

    /**
     * 根据当前的分页参数和排序项，转换为Mybatis Plus的Page对象。
     * 该方法支持手动指定排序方式。
     *
     * @param orderItems 排序项数组，用于指定排序方式。
     * @param <T> 泛型参数，表示页面元素的类型。
     * @return 返回转换后的Page对象。
     */
    public <T> Page<T> toMpPage(OrderItem... orderItems) {
        Page<T> page = new Page<>(pageNo, pageSize);
        // 是否手动指定排序方式
        if (orderItems != null && orderItems.length > 0) {
            for (OrderItem orderItem : orderItems) {
                page.addOrder(orderItem);
            }
            return page;
        }
        // 前端是否有排序字段
        if (StrUtil.isNotEmpty(sortBy)){
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(isAsc);
            orderItem.setColumn(sortBy);
            page.addOrder(orderItem);
        }
        return page;
    }

    /**
     * 根据当前的分页参数和默认排序方式，转换为Mybatis Plus的Page对象。
     * 如果没有指定排序字段，则使用默认排序字段和排序方式。
     *
     * @param defaultSortByFunction 默认的排序字段方法引用。
     * @param isAsc         默认的排序方式，true为升序，false为降序。
     * @param <T>           泛型参数，表示页面元素的类型。
     * @param <R>           实体类的类型
     * @return 返回转换后的Page对象。
     */
    public <T, R> Page<T> toMpPage(LambdaUtils.SFunction<R, ?> defaultSortByFunction, boolean isAsc) {
        if (StringUtils.isBlank(sortBy)){
            sortBy = getFieldName(defaultSortByFunction);
            this.isAsc = isAsc;
        }
        Page<T> page = new Page<>(pageNo, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(this.isAsc);
        orderItem.setColumn(sortBy);
        page.addOrder(orderItem);
        return page;
    }

    /**
     * 根据当前的分页参数和默认排序方式，转换为Mybatis Plus的Page对象。
     * 如果没有指定排序字段，则使用默认排序字段和排序方式。
     *
     * @param defaultSortBy 默认的排序字段。
     * @param isAsc         默认的排序方式，true为升序，false为降序。
     * @param <T>           泛型参数，表示页面元素的类型。
     * @return 返回转换后的Page对象。
     */
    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc) {
        if (StringUtils.isBlank(sortBy)){
            sortBy = defaultSortBy;
            this.isAsc = isAsc;
        }
        Page<T> page = new Page<>(pageNo, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(this.isAsc);
        orderItem.setColumn(sortBy);
        page.addOrder(orderItem);
        return page;
    }


    /**
     * 根据当前的分页参数，转换为Mybatis Plus的Page对象，并指定默认的排序方式为创建时间降序。
     * 这是一个方便的方法调用，用于常见的情况。
     *
     * @param <T> 泛型参数，表示页面元素的类型。
     * @return 返回转换后的Page对象，排序方式为创建时间降序。
     */
    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage("create_time", false);
    }

    /**
     * 根据当前的分页参数，转换为Mybatis Plus的Page对象，并指定默认的排序方式为创建时间降序。
     * 这是一个方便的方法调用，用于常见的情况。
     *
     * @param <T> 泛型参数，表示页面元素的类型。
     * @return 返回转换后的Page对象，排序方式为创建时间降序。
     */
    public <T> Page<T> toMpPageDefaultSortByUpdateTimeDesc() {
        return toMpPage("update_time", false);
    }
}
