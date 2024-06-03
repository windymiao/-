package com.example.university.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.university.common.Result;
import com.example.university.entity.PageParam;
import com.example.university.entity.Sc;
import com.example.university.entity.TCourse;
import com.example.university.service.ScService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author university
 * @since 2024-05-23
 */
@RestController
@RequestMapping("/sc")
public class ScController {

    @Autowired
    private ScService scService;


    // 分页查询所有课程成绩
    @PostMapping("/page")
    public Result getAllSCByPage(@RequestBody PageParam pageParam){
        // 设置分页信息
        Page<Sc> page = new Page<>();
        page.setSize(pageParam.getPageSize());
        page.setCurrent(pageParam.getPageNum());
        HashMap param = pageParam.getParam();
        LambdaQueryWrapper<Sc> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据学生学号精确匹配
        if (!StringUtils.isNullOrEmpty((String) param.get("sno"))){
            lambdaQueryWrapper.like(Sc::getSno,param.get("sno"));
        }
        Page<Sc> coursePage = scService.page(page, lambdaQueryWrapper);
//        if (coursePage.getRecords().size()>0){
        // 当前学生可能未学习任何课程，所以查不出来数据，不能以此判断是否成功
        return Result.success(coursePage.getTotal(),coursePage.getRecords());
//        }
//        return Result.fail();
    }

    // 学生选课功能，根据学生学号sno与课程号cno，添加学生选课记录
    @PostMapping("/save")
    public Result saveSc(@RequestBody Sc sc){
        boolean is = scService.save(sc);
        return is?Result.success():Result.fail();
    }

}
