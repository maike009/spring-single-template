package com.mk.module.service;

import com.mk.pojo.dto.UserDto;
import com.mk.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.pojo.result.PageResult;
import com.mk.pojo.result.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maike
 * @since 2024-07-01
 */
public interface IUserService extends IService<User> {

    Result<PageResult<User>> getUsers(UserDto userDto);
}
