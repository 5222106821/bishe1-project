package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    /**
     * 分页查询所有员工
     * @param page
     * @return List<Employee>
     */
    @Select("select * from employee where " +
            "studentName like concat('%',#{name},'%') " +
            "and grade like concat('%',#{grade},'%') " +
            "and tel like concat('%',#{tel},'%') " +
            "and major like concat('%',#{major},'%') " +
            "and institute like concat('%',#{institute},'%') " +
            "and clazz like concat('%',#{clazz},'%')")
    IPage<Employee> findAll(Page<Employee> page, @Param("name") String name, @Param("grade") String grade,
                           @Param("tel") String tel,  @Param("institute") String institute,
                           @Param("major")String major, @Param("clazz") String clazz);

    @Select("select * from employee where studentId = #{studentId}")
    Employee findById(Integer studentId);

    @Delete("delete from employee where studentId = #{studentId}")
    int deleteById(Integer studentId);

    /**
     *更新所有员工信息
     * @param employee 传递一个对象
     * @return 受影响的记录条数
     */
    @Update("update employee set studentName = #{studentName},grade = #{grade},major = #{major},clazz = #{clazz}," +
            "institute = #{institute},tel = #{tel},email = #{email},pwd = #{pwd},cardId = #{cardId},sex = #{sex},role = #{role} " +
            "where studentId = #{studentId}")
    int update(Employee employee);

    /**
     * 更新密码
     * @param employee
     * @return 受影响的记录条数
     */
    @Update("update employee set pwd = #{pwd} where studentId = #{studentId}")
    int updatePwd(Employee employee);


    @Options(useGeneratedKeys = true,keyProperty = "studentId")
    @Insert("insert into employee(studentName,grade,major,clazz,institute,tel,email,pwd,cardId,sex,role) values " +
            "(#{studentName},#{grade},#{major},#{clazz},#{institute},#{tel},#{email},#{pwd},#{cardId},#{sex},#{role})")
    int add(Employee employee);
}
