package com.example.university.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.university.common.Result;
import com.example.university.entity.Menu;
import com.example.university.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author university
 * @since 2024-05-29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    // 根据权限id，查询menu
    @GetMapping("list")
    public Result list(@RequestParam String roleId){
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Menu::getMenuright,roleId);
        List<Menu> menus = menuService.list(lambdaQueryWrapper);
        return menus.size()>0?Result.success(menus):Result.fail();
    }

}
