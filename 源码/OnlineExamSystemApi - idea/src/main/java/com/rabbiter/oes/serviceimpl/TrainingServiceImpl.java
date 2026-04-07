package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.TrainingCourse;
import com.rabbiter.oes.entity.TrainingPlan;
import com.rabbiter.oes.entity.TrainingRecord;
import com.rabbiter.oes.mapper.TrainingCourseMapper;
import com.rabbiter.oes.mapper.TrainingPlanMapper;
import com.rabbiter.oes.mapper.TrainingRecordMapper;
import com.rabbiter.oes.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingCourseMapper trainingCourseMapper;
    @Autowired
    private TrainingPlanMapper trainingPlanMapper;
    @Autowired
    private TrainingRecordMapper trainingRecordMapper;

    @Override
    public IPage<TrainingCourse> findCoursePage(Page<TrainingCourse> page, String department, String position, String keyword) {
        department = "@".equals(department) ? "" : department;
        position = "@".equals(position) ? "" : position;
        keyword = "@".equals(keyword) ? "" : keyword;
        return trainingCourseMapper.findAll(page, department, position, keyword);
    }

    @Override
    public List<TrainingCourse> findAllCourses() {
        return trainingCourseMapper.findAllList();
    }

    @Override
    public int addCourse(TrainingCourse course) {
        course.setCreateTime(new Date());
        return trainingCourseMapper.add(course);
    }

    @Override
    public int updateCourse(TrainingCourse course) {
        return trainingCourseMapper.update(course);
    }

    @Override
    public int deleteCourse(Long id) {
        return trainingCourseMapper.deleteById(id);
    }

    @Override
    public IPage<Map<String, Object>> findPlanPage(Page<Map<String, Object>> page, String department, String position) {
        department = "@".equals(department) ? "" : department;
        position = "@".equals(position) ? "" : position;
        return trainingPlanMapper.findAll(page, department, position);
    }

    @Override
    public int addPlan(TrainingPlan plan) {
        plan.setCreateTime(new Date());
        return trainingPlanMapper.add(plan);
    }

    @Override
    public int updatePlan(TrainingPlan plan) {
        return trainingPlanMapper.update(plan);
    }

    @Override
    public int deletePlan(Long id) {
        return trainingPlanMapper.deleteById(id);
    }

    @Override
    public IPage<Map<String, Object>> findEmployeePlans(Page<Map<String, Object>> page, Integer employeeId) {
        return trainingPlanMapper.findEmployeePlans(page, employeeId);
    }

    @Override
    public TrainingRecord saveOrUpdateRecord(TrainingRecord incoming, Integer durationIncrement) {
        TrainingRecord existing = trainingRecordMapper.findByPlanAndEmployee(incoming.getPlanId(), incoming.getEmployeeId());
        Date now = new Date();
        int completion = incoming.getCompletionPercent() == null ? 0 : incoming.getCompletionPercent();
        if (completion > 100) {
            completion = 100;
        }
        if (completion < 0) {
            completion = 0;
        }

        if (existing == null) {
            TrainingRecord record = new TrainingRecord();
            record.setPlanId(incoming.getPlanId());
            record.setCourseId(incoming.getCourseId());
            record.setEmployeeId(incoming.getEmployeeId());
            record.setLearningDuration(Math.max(durationIncrement == null ? 0 : durationIncrement, 0));
            record.setCompletionPercent(completion);
            record.setCompleted(completion >= 100 ? 1 : 0);
            record.setLastStudyTime(now);
            record.setCreateTime(now);
            record.setUpdateTime(now);
            trainingRecordMapper.add(record);
            return record;
        }

        int oldDuration = existing.getLearningDuration() == null ? 0 : existing.getLearningDuration();
        int inc = Math.max(durationIncrement == null ? 0 : durationIncrement, 0);
        existing.setLearningDuration(oldDuration + inc);
        existing.setCompletionPercent(completion);
        existing.setCompleted(completion >= 100 ? 1 : 0);
        existing.setLastStudyTime(now);
        existing.setUpdateTime(now);
        trainingRecordMapper.update(existing);
        return existing;
    }

    @Override
    public IPage<Map<String, Object>> findEmployeeRecords(Page<Map<String, Object>> page, Integer employeeId) {
        return trainingRecordMapper.findByEmployee(page, employeeId);
    }

    @Override
    public IPage<Map<String, Object>> findPlanRecords(Page<Map<String, Object>> page, Long planId) {
        return trainingRecordMapper.findByPlan(page, planId);
    }

    @Override
    public Map<String, Object> statByPlan(Long planId) {
        return trainingRecordMapper.statByPlan(planId);
    }
}

