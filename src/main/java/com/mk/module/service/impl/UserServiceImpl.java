package com.mk.module.service.impl;

import com.mk.pojo.entity.User;
import com.mk.module.mapper.UserMapper;
import com.mk.module.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
