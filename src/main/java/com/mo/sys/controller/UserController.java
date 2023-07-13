package com.mo.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mo.common.vo.Result;
import com.mo.sys.entity.User;
import com.mo.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
@Api(tags = {"用户接口列表"}) //swagger
@RestController
@RequestMapping("/user")
//@CrossOrigin 跨域处理方式一
//@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("获取所有的用户")
    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list,"查询成功！");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }

        return Result.fail(20002,"用户名或密码错误");
    }

    @ApiOperation("根据token获取用户")
    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
//        根据token 获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }

        return Result.fail(20003,"登录信息无效，请重新登录");
    }
    @ApiOperation("注销")
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
       userService.logout(token);
       return Result.success();
    }

//    用户列表查询
    @ApiOperation("查询用户列表")
    @GetMapping("/list")
    public Result<?> getUserList(@RequestParam(value = "username",required = false) String username,
                                              @RequestParam(value = "phone",required = false) String phone,
                                              @RequestParam("pageNo") Long pageNo,
                                              @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
//        降序排序
        wrapper.orderByDesc(User::getId);
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);


    }
//    新增用户
    @ApiOperation("新增用户")
    @PostMapping
    public Result<?> addUser(@RequestBody User user){
//      使用passwordEncoder 进行加密 ，哪怕传入的明文一样，加密出来的内容都是不一样的
        user.setPassword(passwordEncoder.encode(user.getPassword()));//密码加密
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    //    修改用户
    @ApiOperation("修改用户")
    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }
//根据用户id 查询用户信息
    @ApiOperation("根据用户id 查询用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);
    return  Result.success(user);
    }
//删除用户
    @ApiOperation("根据用户id,删除用户")
    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
       userService.deleteUserById(id);
        return  Result.success("删除用户成功");
    }


}
