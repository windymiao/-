package com.example.university.mapper;

import com.example.university.entity.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.university.entity.ScheduleRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author university
 * @since 2024-06-01
 */
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {

    List<ScheduleRes> selectScheduleByClazzId(String clazzId);
}

