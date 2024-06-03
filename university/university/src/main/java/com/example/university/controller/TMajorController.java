package com.example.university.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.university.common.Result;
import com.example.university.entity.TDept;
import com.example.university.entity.TMajor;
import com.example.university.service.TDeptService;
import com.example.university.service.TMajorService;
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
 * @since 2024-05-23
 */
@RestController
@RequestMapping("/major")
public class TMajorController {

    @Autowired
    private TMajorService majorService;

    // 查询所有的major信息
    @GetMapping("/list")
    public Result list(){
        List<TMajor> list = majorService.list();
        return list.size()>0?Result.success(list):Result.fail();
    }

    // 根据id查询major信息
    @GetMapping("/one")
    public Result one(@RequestParam Integer majorId){
        if (majorId!=null){
            TMajor major = majorService.lambdaQuery().eq(TMajor::getMajorId, majorId).one();
            return major!=null?Result.success(major):Result.fail();
        }
        return Result.fail();
    }

    // 根据deptId查询major信息
    @GetMapping("/byDept")
    public Result getMajorsByDeptId(@RequestParam String deptId){
        LambdaQueryWrapper<TMajor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (deptId != null){
            lambdaQueryWrapper.eq(TMajor::getDeptId,deptId);
        }
        List<TMajor> majors = majorService.list(lambdaQueryWrapper);

        return majors.size()>0?Result.success(majors):Result.fail();
    }

}
