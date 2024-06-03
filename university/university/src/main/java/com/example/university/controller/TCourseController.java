package com.example.university.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.university.common.Result;
import com.example.university.entity.PageParam;
import com.example.university.entity.Sc;
import com.example.university.entity.TCourse;
import com.example.university.entity.TStudent;
import com.example.university.service.ScService;
import com.example.university.service.TCourseService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/course")
public class TCourseController {

    @Autowired
    private TCourseService courseService;
    @Autowired
    private ScService scService;

    // 查询所有课程信息
    @GetMapping("/list")
    public Result getAllCourse(){
        List<TCourse> list = courseService.list();
        return list.size()>0?Result.success(list.size(),list):Result.fail();
    }

    // 新增课程信息
    @PostMapping("/add")
    public Result addCourse(@RequestBody TCourse course){
        boolean is = courseService.save(course);
        return is?Result.success():Result.fail();
    }
    // 修改课程信息
    @PostMapping("/update")
    public Result updateCourse(@RequestBody TCourse course){
        boolean is = courseService.updateById(course);
        return is?Result.success():Result.fail();
    }
    // 删除课程信息
    @GetMapping("/delete")
    public Result deleteCourse(@RequestParam String cno){
        boolean is = courseService.removeById(cno);
        return is?Result.success():Result.fail();
    }
    // 根据课程号，查询课程
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String cno){
        TCourse course = courseService.lambdaQuery().eq(TCourse::getCno, cno).one();
        return course!=null?Result.success(course):Result.fail();
    }
    // 分页查询所有课程信息
    @PostMapping("/page")
    public Result getAllCourseByPage(@RequestBody PageParam pageParam){
        // 设置分页信息
        Page<TCourse> page = new Page<>();
        page.setSize(pageParam.getPageSize());
        page.setCurrent(pageParam.getPageNum());
        HashMap param = pageParam.getParam();
        LambdaQueryWrapper<TCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 模糊匹配课程名
        if (!StringUtils.isNullOrEmpty((String) param.get("cname"))){
            lambdaQueryWrapper.like(TCourse::getCname,param.get("cname"));
        }
        // 精确匹配系部id
        if (param.get("deptId") != null && param.get("deptId") != ""){
            lambdaQueryWrapper.eq(TCourse::getDeptId,param.get("deptId"));
        }
        Page<TCourse> coursePage = courseService.page(page, lambdaQueryWrapper);
        if (coursePage.getRecords().size()>0){
            return Result.success(coursePage.getTotal(),coursePage.getRecords());
        }
        return Result.fail();
    }

    // 学生选课功能，查询所有该学生所在系部，当前学生未选修的课程
    @PostMapping("/getCourse")
    public Result getAllCourseByStudent(@RequestBody PageParam pageParam){
        // 设置分页信息
        Page<TCourse> page = new Page<>();
        page.setSize(pageParam.getPageSize());
        page.setCurrent(pageParam.getPageNum());
        HashMap param = pageParam.getParam();
        LambdaQueryWrapper<TCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据学生id，去sc表查询所有该学生已经选修的课程号cno
        // 根据学号，查询所有当前学生已经学习的课程
        List<Sc> scList = scService.lambdaQuery().eq(Sc::getSno, param.get("sno")).select(Sc::getCno).list();
        // cnoList为空是会报错 :  WHERE (cno NOT IN () AND dept_id = ?)", exception:
        if (scList.size()>0){
            // 设置查询条件，将该学生已经学习的课程筛选掉
            List<Integer> cnoList = new ArrayList<>();
            for (Sc sc : scList) {
                cnoList.add(sc.getCno());
            }
            lambdaQueryWrapper.notIn(TCourse::getCno,cnoList);
        }
        // 精确匹配系部id
        if (param.get("deptId") != null && param.get("deptId") != ""){
            lambdaQueryWrapper.eq(TCourse::getDeptId,param.get("deptId"));
        }
        Page<TCourse> coursePage = courseService.page(page, lambdaQueryWrapper);
        if (coursePage.getRecords().size()>0){
            return Result.success(coursePage.getTotal(),coursePage.getRecords());
        }
        return Result.fail();
    }

}
