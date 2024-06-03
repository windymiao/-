package com.example.university.service;

import com.example.university.entity.Schedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.university.entity.ScheduleRes;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author university
 * @since 2024-06-01
 */
public interface ScheduleService extends IService<Schedule> {

    List<ScheduleRes> getScheduleByClazzId(String clazzId);
}

