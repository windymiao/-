package com.example.university.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.university.common.Result;
import com.example.university.entity.PageParam;
import com.example.university.entity.TDept;
import com.example.university.entity.TMajor;
import com.example.university.entity.TStudent;
import com.example.university.service.TDeptService;
import com.example.university.service.TMajorService;
import com.example.university.service.TStudentService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/student")
public class TStudentController {

    @Autowired
    private TStudentService studentService;
    @Autowired
    private TDeptService deptService;
    @Autowired
    private TMajorService majorService;

    // 查询所有学生信息
    @GetMapping("/list")
    public Result getAllStudent(){
        List<TStudent> list = studentService.list();
        return list.size()>0?Result.success(list.size(),list):Result.fail();
    }

    // 新增学生信息
    @PostMapping("/add")
    public Result addStudent(@RequestBody TStudent student){
        boolean is = studentService.save(student);
        return is?Result.success():Result.fail();
    }

    // 更新学生信息
    @PostMapping("/update")
    public Result updateStudent(@RequestBody TStudent student){
        boolean is = studentService.updateById(student);
        return is?Result.success():Result.fail();
    }

    @GetMapping("/delete")
    public Result deleteStudent(@RequestParam String sno){
        boolean is = studentService.removeById(sno);
        return is?Result.success():Result.fail();
    }

    // 保存学生信息，新增或更新  （合在一起写，存在判断账号是否存在有误的问题）
    @PostMapping("/save")
    public Result saveStudent(@RequestBody TStudent student){
        boolean is = studentService.saveOrUpdate(student);
        return is?Result.success():Result.fail();
    }

    // 根据学号查询学生
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String sno){
        TStudent student = studentService.lambdaQuery().eq(TStudent::getSno, sno).one();
        return student!=null?Result.success(student):Result.fail();
    }

    // 根据传入条件，分页查询所有学生信息
    @PostMapping("/page")
    public Result getAllUserByPage(@RequestBody PageParam pageParam){
        // 设置分页信息
        Page<TStudent> page = new Page<>();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());
        // 设置查询条件
        LambdaQueryWrapper<TStudent> queryWrapper = new LambdaQueryWrapper<>();
        HashMap param = pageParam.getParam();
//        System.out.println("param"+param);
        // 根据姓名，模糊查询
        if (!StringUtils.isNullOrEmpty((String) param.get("name"))){
            queryWrapper.like(TStudent::getSname,param.get("name"));
//            System.out.println("name"+param.get("name"));
        }
//        System.out.println("没进去"+param.get("name"));
        // 根据性别，匹配查询
        if (!StringUtils.isNullOrEmpty((String) param.get("gender"))){
            queryWrapper.eq(TStudent::getGender,param.get("gender"));
        }
        // 根据年级查询
        if (!StringUtils.isNullOrEmpty((String) param.get("grade"))){
            queryWrapper.eq(TStudent::getGrade,param.get("grade"));
        }
        // 根据系别名查询  (前端直接传入id，直接根据id查询)
//        if (!StringUtils.isNullOrEmpty((String) param.get("deptId"))){
        if (param.get("deptId") != null && param.get("deptId") != ""){
            queryWrapper.eq(TStudent::getDeptId,param.get("deptId"));
           /* TDept dept = deptService.lambdaQuery().eq(TDept::getDeptName, param.get("deptName")).one();
            if (dept != null){
                queryWrapper.eq(TStudent::getDeptId,dept.getDeptId());
            }*/
        }
        // 根据专业名查询  (前端直接传入id，直接根据id查询)
//        if (!StringUtils.isNullOrEmpty((String) param.get("majorId"))){
        if (param.get("majorId") != null && param.get("majorId") != ""){
            queryWrapper.eq(TStudent::getMajorId,param.get("majorId"));
            /*TMajor major = majorService.lambdaQuery().eq(TMajor::getMajorName, param.get("majorName")).one();
            if (major != null){
                queryWrapper.eq(TStudent::getMajorId,major.getMajorId());
            }*/
        }
        // 执行查询语句
        Page<TStudent> studentPage = studentService.page(page,queryWrapper);
        if (studentPage.getRecords().size() > 0){
            return Result.success(studentPage.getTotal(),studentPage.getRecords());
        }
        return Result.fail();
    }
}
