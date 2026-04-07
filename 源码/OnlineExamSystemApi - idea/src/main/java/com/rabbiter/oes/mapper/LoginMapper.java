package com.rabbiter.oes.mapper;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.Employee;
import com.rabbiter.oes.entity.DepartmentManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select adminId,adminName,sex,tel,email,cardId,role from `admin` where adminId = #{username} and pwd = #{password}")
    public Admin adminLogin(@Param("username") Integer username, @Param("password") String password);

    @Select("select teacherId,teacherName,institute,sex,tel,email,cardId," +
            "type,role from department_manager where teacherId = #{username} and pwd = #{password}")
    public DepartmentManager departmentManagerLogin(@Param("username") Integer username, @Param("password") String password);

    @Select("select studentId,studentName,grade,major,clazz,institute,tel," +
            "email,cardId,sex,role from employee where studentId = #{username} and pwd = #{password}")
    public Employee employeeLogin(@Param("username") Integer username, @Param("password") String password);
}
