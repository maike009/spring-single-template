package com.mk.module.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.module.mapper.OperateLogMapper;
import com.mk.module.service.IOperateLogService;
import com.mk.pojo.entity.OperateLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author maike
 * @since 2024-06-01
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
