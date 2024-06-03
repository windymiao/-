package com.example.university.controller;


import com.example.university.common.Result;
import com.example.university.entity.Clazz;
import com.example.university.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author university
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/list")
    public Result list(){
        List<Clazz> clazzList = clazzService.list();
        return clazzList.size()>0?Result.success(clazzList):Result.fail();
    }

}
