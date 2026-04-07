package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.Employee;
import com.rabbiter.oes.entity.DepartmentManager;
import com.rabbiter.oes.mapper.LoginMapper;
import com.rabbiter.oes.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Admin adminLogin(Integer username, String password) {
        return loginMapper.adminLogin(username,password);
    }

    @Override
    public DepartmentManager departmentManagerLogin(Integer username, String password) {
        return loginMapper.departmentManagerLogin(username,password);
    }

    @Override
    public Employee employeeLogin(Integer username, String password) {
        return loginMapper.employeeLogin(username,password);
    }
}
