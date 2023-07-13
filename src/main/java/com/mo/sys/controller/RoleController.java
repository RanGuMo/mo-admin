package com.mo.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mo.common.vo.Result;
import com.mo.sys.entity.Role;
import com.mo.sys.entity.User;
import com.mo.sys.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = {"角色接口列表"}) //swagger
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;


    //    用户列表查询
    @ApiOperation("查询角色列表")
    @GetMapping("/list")
    public Result<?> getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                 @RequestParam("pageNo") Long pageNo,
                                 @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(roleName), Role::getRoleName, roleName);
//        降序排序
        wrapper.orderByDesc(Role::getRoleId);
        Page<Role> page = new Page<>(pageNo, pageSize);
        roleService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);


    }

    //    新增角色
    @ApiOperation("新增角色")
    @PostMapping
    public Result<?> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return Result.success("新增角色成功");
    }

    //    修改角色
    @ApiOperation("修改角色")
    @PutMapping
    public Result<?> updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        return Result.success("修改角色成功");
    }
    //根据角色id 查询角色信息
    @ApiOperation("根据角色id 查询角色信息")
    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable("id") Integer id){
        Role role = roleService.getRoleById(id);
        return  Result.success(role);
    }
    //删除角色
    @ApiOperation("根据角色id,删除角色")
    @DeleteMapping("/{id}")
    public Result<Role> deleteRoleById(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return  Result.success("删除角色成功");
    }

    @ApiOperation("获取所有角色名称")
    @GetMapping("/all")
    public Result<List<Role>> getAllRole(){
        List<Role> roleList = roleService.list();
        return  Result.success(roleList);
    }





}
