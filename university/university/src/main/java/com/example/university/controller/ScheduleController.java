package com.example.university.controller;


import com.example.university.common.Result;
import com.example.university.entity.Schedule;
import com.example.university.entity.ScheduleRes;
import com.example.university.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 新增课表  （根据班级id）
    @PostMapping("/add")
    public Result addSchedule(@RequestBody Schedule schedule){
//        schedule.setScheduleId();
        boolean is = scheduleService.save(schedule);
        return is?Result.success():Result.fail();
    }
    // 修改课表  （根据班级id）
    @PostMapping("/update")
    public Result updateSchedule(@RequestBody Schedule schedule){
        boolean is = scheduleService.updateById(schedule);
        return is?Result.success():Result.fail();
    }
    // 新增或修改课表
    @PostMapping("/save")
    public Result saveSchedule(@RequestBody Schedule schedule){
        boolean is = scheduleService.saveOrUpdate(schedule);
        return is?Result.success():Result.fail();
    }
    // 删除课表  （根据班级id）
    @GetMapping("/delete")
    public Result deleteSchedule(@RequestParam String scheduleId){
        boolean is = scheduleService.removeById(scheduleId);
        return is?Result.success():Result.fail();
    }

    // 根据班级id查询所有的课程表
    @GetMapping("/list")
    public Result getAllSchedule(@RequestParam String clazzId){
//        List<Schedule> scheduleList = scheduleService.lambdaQuery().eq(Schedule::getClazzId, clazzId).list();
//        return scheduleList.size()>0?Result.success(scheduleList):Result.fail();
        List<ScheduleRes> list =  scheduleService.getScheduleByClazzId(clazzId);
        return list.size()>0?Result.success(list):Result.fail();
//        return Result.success(list);
    }
}
