package com.mk.module.mapper;

import com.mk.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maike
 * @since 2024-07-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
