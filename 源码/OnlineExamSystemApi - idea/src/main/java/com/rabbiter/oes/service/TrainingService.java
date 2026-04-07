package com.rabbiter.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.TrainingCourse;
import com.rabbiter.oes.entity.TrainingPlan;
import com.rabbiter.oes.entity.TrainingRecord;

import java.util.List;
import java.util.Map;

public interface TrainingService {
    IPage<TrainingCourse> findCoursePage(Page<TrainingCourse> page, String department, String position, String keyword);

    List<TrainingCourse> findAllCourses();

    int addCourse(TrainingCourse course);

    int updateCourse(TrainingCourse course);

    int deleteCourse(Long id);

    IPage<Map<String, Object>> findPlanPage(Page<Map<String, Object>> page, String department, String position);

    int addPlan(TrainingPlan plan);

    int updatePlan(TrainingPlan plan);

    int deletePlan(Long id);

    IPage<Map<String, Object>> findEmployeePlans(Page<Map<String, Object>> page, Integer employeeId);

    TrainingRecord saveOrUpdateRecord(TrainingRecord incoming, Integer durationIncrement);

    IPage<Map<String, Object>> findEmployeeRecords(Page<Map<String, Object>> page, Integer employeeId);

    IPage<Map<String, Object>> findPlanRecords(Page<Map<String, Object>> page, Long planId);

    Map<String, Object> statByPlan(Long planId);
}

