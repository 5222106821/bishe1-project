package com.rabbiter.oes.service;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.Employee;
import com.rabbiter.oes.entity.DepartmentManager;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public DepartmentManager departmentManagerLogin(Integer username, String password);

    public Employee employeeLogin(Integer username, String password);
}
