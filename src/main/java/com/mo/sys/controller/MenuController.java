package com.mo.sys.controller;

import com.mo.common.vo.Result;
import com.mo.sys.entity.Menu;
import com.mo.sys.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
@Api(tags = {"菜单接口列表"}) //swagger
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("查询所有菜单数据")
    @GetMapping("/list")
    public Result<List<Menu>> getAllMenu(){
       List<Menu> menuList = menuService.getAllMenu();
       return Result.success(menuList);
    }

}
