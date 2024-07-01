package com.mk.module.controller;


import com.mk.module.service.IUserService;
import com.mk.pojo.entity.User;
import com.mk.pojo.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mk.pojo.result.Result.success;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author maike
 * @since 2024-07-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
//    @OperationLog(module = "员工管理", operation = OperationType.ADD)
    public Result<List<User>> getUsers(){
        List<User> list = userService.list();
        return success(list);
    }





}
