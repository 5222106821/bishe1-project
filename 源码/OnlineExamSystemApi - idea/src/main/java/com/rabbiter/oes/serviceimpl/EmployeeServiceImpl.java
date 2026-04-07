package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Employee;
import com.rabbiter.oes.mapper.EmployeeMapper;
import com.rabbiter.oes.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public IPage<Employee> findAll(
            Page<Employee> page, String name, String grade,
            String tel, String institute, String major, String clazz) {
        name = ("@".equals(name) ? "" : name);
        grade = ("@".equals(grade) ? "" : grade);
        tel = ("@".equals(tel) ? "" : tel);
        institute = ("@".equals(institute) ? "" : institute);
        major = ("@".equals(major) ? "" : major);
        clazz = ("@".equals(clazz) ? "" : clazz);
        return employeeMapper.findAll(page, name, grade, tel, institute, major, clazz);
    }

    @Override
    public Employee findById(Integer studentId) {
        return employeeMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer studentId) {
        return employeeMapper.deleteById(studentId);
    }

    @Override
    public int update(Employee employee) {
        return employeeMapper.update(employee);
    }

    @Override
    public int updatePwd(Employee employee) {
        return employeeMapper.updatePwd(employee);
    }

    @Override
    public int add(Employee employee) {
        return employeeMapper.add(employee);
    }
}
