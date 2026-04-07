package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.TrainingCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainingCourseMapper {

    @Select("select * from training_course where " +
            "courseName like concat('%',#{keyword},'%') and " +
            "department like concat('%',#{department},'%') and " +
            "position like concat('%',#{position},'%') order by id desc")
    IPage<TrainingCourse> findAll(Page<TrainingCourse> page,
                                  @Param("department") String department,
                                  @Param("position") String position,
                                  @Param("keyword") String keyword);

    @Select("select * from training_course order by id desc")
    List<TrainingCourse> findAllList();

    @Select("select * from training_course where id = #{id}")
    TrainingCourse findById(@Param("id") Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into training_course(courseName,fileName,fileUrl,fileType,department,position,description,creatorId,createTime) " +
            "values(#{courseName},#{fileName},#{fileUrl},#{fileType},#{department},#{position},#{description},#{creatorId},#{createTime})")
    int add(TrainingCourse course);

    @Update("update training_course set courseName=#{courseName},fileName=#{fileName},fileUrl=#{fileUrl},fileType=#{fileType}," +
            "department=#{department},position=#{position},description=#{description} where id=#{id}")
    int update(TrainingCourse course);

    @Delete("delete from training_course where id = #{id}")
    int deleteById(@Param("id") Long id);
}

