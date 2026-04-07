package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.TrainingPlan;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface TrainingPlanMapper {

    @Select("select p.*,c.courseName,c.fileType from training_plan p " +
            "left join training_course c on p.courseId = c.id " +
            "where p.department like concat('%',#{department},'%') " +
            "and p.position like concat('%',#{position},'%') order by p.id desc")
    IPage<Map<String, Object>> findAll(Page<Map<String, Object>> page,
                                       @Param("department") String department,
                                       @Param("position") String position);

    @Select("select * from training_plan where id = #{id}")
    TrainingPlan findById(@Param("id") Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into training_plan(planName,courseId,department,position,startTime,endTime,creatorId,createTime) " +
            "values(#{planName},#{courseId},#{department},#{position},#{startTime},#{endTime},#{creatorId},#{createTime})")
    int add(TrainingPlan plan);

    @Update("update training_plan set planName=#{planName},courseId=#{courseId},department=#{department},position=#{position}," +
            "startTime=#{startTime},endTime=#{endTime} where id=#{id}")
    int update(TrainingPlan plan);

    @Delete("delete from training_plan where id = #{id}")
    int deleteById(@Param("id") Long id);

    @Select("select p.id as planId,p.planName,p.courseId,p.department,p.position,p.startTime,p.endTime," +
            "c.courseName,c.fileName,c.fileUrl,c.fileType," +
            "ifnull(r.learningDuration,0) as learningDuration,ifnull(r.completionPercent,0) as completionPercent,ifnull(r.completed,0) as completed " +
            "from training_plan p " +
            "left join training_course c on p.courseId = c.id " +
            "join employee e on e.studentId = #{employeeId} " +
            "left join training_record r on r.planId = p.id and r.employeeId = e.studentId " +
            "where (p.department is null or p.department = '' or p.department = e.institute) " +
            "and (p.position is null or p.position = '' or p.position = e.major) " +
            "order by p.id desc")
    IPage<Map<String, Object>> findEmployeePlans(Page<Map<String, Object>> page, @Param("employeeId") Integer employeeId);
}

