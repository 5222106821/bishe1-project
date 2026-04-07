package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DepartmentManager;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DepartmentManagerMapper {

    @Select("select * from department_manager")
    IPage<DepartmentManager>  findAll(Page<DepartmentManager> page);

//    @Select("select * from department_manager")
//    public List<DepartmentManager> findAll();

    @Select("select * from department_manager where teacherId = #{teacherId}")
    public DepartmentManager findById(Integer teacherId);

    @Delete("delete from department_manager where teacherId = #{teacherId}")
    public int deleteById(Integer teacherId);

    @Update("update department_manager set teacherName = #{teacherName},sex = #{sex}," +
            "tel = #{tel}, email = #{email},pwd = #{pwd},cardId = #{cardId}," +
            "role = #{role},institute = #{institute},type = #{type} where teacherId = #{teacherId}")
    public int update(DepartmentManager departmentManager);

    @Options(useGeneratedKeys = true,keyProperty = "teacherId")
    @Insert("insert into department_manager(teacherName,sex,tel,email,pwd,cardId,role,type,institute) " +
            "values(#{teacherName},#{sex},#{tel},#{email},#{pwd},#{cardId},#{role},#{type},#{institute})")
    public int add(DepartmentManager departmentManager);
}
