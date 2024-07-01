package com.mk.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mk.pojo.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志表 Mapper 接口
 *
 * @author maike
 * @since 2024-05-28
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}
