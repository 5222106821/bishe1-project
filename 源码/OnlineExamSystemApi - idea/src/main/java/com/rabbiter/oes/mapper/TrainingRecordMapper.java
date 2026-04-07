package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.TrainingRecord;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface TrainingRecordMapper {

    @Select("select * from training_record where planId = #{planId} and employeeId = #{employeeId} limit 1")
    TrainingRecord findByPlanAndEmployee(@Param("planId") Long planId, @Param("employeeId") Integer employeeId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into training_record(planId,courseId,employeeId,learningDuration,completionPercent,completed,lastStudyTime,createTime,updateTime) " +
            "values(#{planId},#{courseId},#{employeeId},#{learningDuration},#{completionPercent},#{completed},#{lastStudyTime},#{createTime},#{updateTime})")
    int add(TrainingRecord record);

    @Update("update training_record set learningDuration=#{learningDuration},completionPercent=#{completionPercent},completed=#{completed}," +
            "lastStudyTime=#{lastStudyTime},updateTime=#{updateTime} where id=#{id}")
    int update(TrainingRecord record);

    @Select("select r.*,p.planName,c.courseName from training_record r " +
            "left join training_plan p on r.planId = p.id " +
            "left join training_course c on r.courseId = c.id " +
            "where r.employeeId = #{employeeId} order by r.updateTime desc")
    IPage<Map<String, Object>> findByEmployee(Page<Map<String, Object>> page, @Param("employeeId") Integer employeeId);

    @Select("select r.id,r.planId,p.planName,r.employeeId,e.studentName,e.institute,e.major," +
            "r.learningDuration,r.completionPercent,r.completed,r.lastStudyTime " +
            "from training_record r " +
            "left join employee e on r.employeeId = e.studentId " +
            "left join training_plan p on r.planId = p.id " +
            "where r.planId = #{planId} order by r.updateTime desc")
    IPage<Map<String, Object>> findByPlan(Page<Map<String, Object>> page, @Param("planId") Long planId);

    @Select("select " +
            "count(1) as totalEmployees, " +
            "sum(case when completed = 1 then 1 else 0 end) as completedEmployees, " +
            "round(avg(ifnull(completionPercent,0)),2) as avgCompletionPercent, " +
            "sum(ifnull(learningDuration,0)) as totalLearningDuration " +
            "from training_record where planId = #{planId}")
    Map<String, Object> statByPlan(@Param("planId") Long planId);
}

