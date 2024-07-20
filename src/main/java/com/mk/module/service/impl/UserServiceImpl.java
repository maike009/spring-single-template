package com.mk.module.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.module.mapper.UserMapper;
import com.mk.module.service.IUserService;
import com.mk.pojo.dto.UserDto;
import com.mk.pojo.entity.User;
import com.mk.pojo.result.PageResult;
import com.mk.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maike
 * @since 2024-07-01
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Result<PageResult<User>> getUsers(UserDto userDto) {
        // 分页条件
        Page<User> page = userDto.toMpPageConditions(User::getId,userDto.getIsAsc());

        // 分页查询结果
        Page<User> p = lambdaQuery()
                .like(userDto.getName() != null,User::getName, userDto.getName())
                .page(page);
        // 封装vo类
        PageResult<User> pageResult = PageResult.of(p,User.class);

        return Result.success(pageResult);
    }
}
