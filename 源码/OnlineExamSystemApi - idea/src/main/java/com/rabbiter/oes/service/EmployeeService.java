package com.rabbiter.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Employee;

public interface EmployeeService {

    IPage<Employee> findAll(Page<Employee> page, String name, String grade,
                           String tel, String institute, String major, String clazz);

    Employee findById(Integer studentId);

    int deleteById(Integer studentId);

    int update(Employee employee);

    int updatePwd(Employee employee);
    int add(Employee employee);
}
