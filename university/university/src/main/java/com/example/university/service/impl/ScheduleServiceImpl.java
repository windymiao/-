package com.example.university.service.impl;

import com.example.university.entity.Schedule;
import com.example.university.entity.ScheduleRes;
import com.example.university.mapper.ScheduleMapper;
import com.example.university.service.ScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author university
 * @since 2024-06-01
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleRes> getScheduleByClazzId(String clazzId) {
        return scheduleMapper.selectScheduleByClazzId(clazzId);
    }
}
