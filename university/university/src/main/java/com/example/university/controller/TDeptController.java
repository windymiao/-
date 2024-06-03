package com.example.university.controller;


import com.example.university.common.Result;
import com.example.university.entity.TDept;
import com.example.university.service.TDeptService;
import io.swagger.models.auth.In;
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
@RequestMapping("/dept")
public class TDeptController {

    @Autowired
    private TDeptService deptService;

    // 查询所有的dept信息
    @GetMapping("/list")
    public Result list(){
        List<TDept> list = deptService.list();
        return list.size()>0?Result.success(list):Result.fail();
    }

    // 根据id查询dept信息
    @GetMapping("/one")
    public Result one(@RequestParam Integer deptId){
        if (deptId != null){
            TDept dept = deptService.lambdaQuery().eq(TDept::getDeptId, deptId).one();
            return dept!=null?Result.success(dept):Result.fail();
        }
        return Result.fail();
    }

}
