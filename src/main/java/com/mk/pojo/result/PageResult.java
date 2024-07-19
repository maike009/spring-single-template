package com.mk.pojo.result;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果类，用于封装分页查询的数据。
 * 包含当前页的数据列表、总数据量和总页数。
 *
 * @param <T> 泛型参数，表示列表中元素的类型。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 总数据量
     */
    protected Long total;
    /**
     * 总页数
     */
    protected Long pages;
    /**
     * 存储查询结果的具体数据
     */
    protected List<T> list;

    /**
     * 根据Page对象和VO类的Class对象，转换生成PageResult对象。
     * 这个方法主要用于将分页查询的结果转换为指定的VO对象进行返回。
     *
     * @param p 分页查询的结果，包含数据记录列表和分页信息。
     * @param clazz VO对象的Class对象，用于指定将数据记录转换为目标类型。
     * @param <ENTITY> 数据实体的泛型类型。
     * @param <VO> 转换后对象的泛型类型。
     * @return 返回转换后的PageResult对象，包含分页信息和转换后的数据记录列表。
     */
    public static <ENTITY,VO> PageResult<VO> of(Page<ENTITY> p, Class<VO> clazz) {
        PageResult<VO> pageResult = new PageResult<>();
        // 设置分页结果的总记录数和总页数
        pageResult.setTotal(p.getTotal());
        pageResult.setPages(p.getPages());
        List<ENTITY> records = p.getRecords();
        // 如果记录列表为空，直接返回空的PageResult
        if (CollectionUtil.isEmpty(records)){
            pageResult.setList(Collections.emptyList());
            return pageResult;
        }
        // 通过BeanUtil工具类将实体类转换为VO类
        pageResult.setList(BeanUtil.copyToList(records,clazz));
        return pageResult;
    }

    /**
     * 根据Page对象和一个转换函数，转换生成PageResult对象。
     * 这个方法主要用于将分页查询的结果使用提供的转换函数进行转换后返回。
     *
     * @param p 分页查询的结果，包含数据记录列表和分页信息。
     * @param convertor 转换函数，用于将每个实体对象转换为目标类型。
     * @param <ENTITY> 数据实体的泛型类型。
     * @param <VO> 转换后对象的泛型类型。
     * @return 返回转换后的PageResult对象，包含分页信息和转换后的数据记录列表。
     */
    public static <ENTITY,VO> PageResult<VO> of(Page<ENTITY> p, Function<ENTITY,VO> convertor) {
        PageResult<VO> pageResult = new PageResult<>();
        // 设置分页结果的总记录数和总页数
        pageResult.setTotal(p.getTotal());
        pageResult.setPages(p.getPages());
        List<ENTITY> records = p.getRecords();
        // 如果记录列表为空，直接返回空的PageResult
        if (CollectionUtil.isEmpty(records)){
            pageResult.setList(Collections.emptyList());
            return pageResult;
        }
        // 使用流式编程和转换函数将实体列表转换为VO列表
        pageResult.setList(records.stream().map(convertor).collect(Collectors.toList()));
        return pageResult;
    }

}
