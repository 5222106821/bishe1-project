package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.Employee;
import com.rabbiter.oes.serviceimpl.EmployeeServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/students/{page}/{size}/{name}/{grade}/{tel}/{institute}/{major}/{clazz}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable  String name, @PathVariable String grade,
                             @PathVariable String tel, @PathVariable String institute,
                             @PathVariable String major, @PathVariable String clazz) {
        Page<Employee> employeePage = new Page<>(page,size);
        IPage<Employee> res = employeeService.findAll(
                employeePage, name, grade, tel, institute, major, clazz
        );
        return  ApiResultHandler.buildApiResult(200,"分页查询所有员工",res);
    }

    @GetMapping("/employees/{page}/{size}/{name}/{grade}/{tel}/{institute}/{major}/{clazz}")
    public ApiResult findAllEmployees(@PathVariable Integer page, @PathVariable Integer size,
                                      @PathVariable String name, @PathVariable String grade,
                                      @PathVariable String tel, @PathVariable String institute,
                                      @PathVariable String major, @PathVariable String clazz) {
        return findAll(page, size, name, grade, tel, institute, major, clazz);
    }

    @GetMapping("/student/{studentId}")
    public ApiResult findById(@PathVariable("studentId") Integer studentId) {
        Employee res = employeeService.findById(studentId);
        if (res != null) {
        return ApiResultHandler.buildApiResult(200,"请求成功",res);
        } else {
            return ApiResultHandler.buildApiResult(404,"查询的用户不存在",null);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ApiResult findEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return findById(employeeId);
    }

    @DeleteMapping("/student/{studentId}")
    public ApiResult deleteById(@PathVariable("studentId") Integer studentId) {
        return ApiResultHandler.buildApiResult(200,"删除成功",employeeService.deleteById(studentId));
    }

    @DeleteMapping("/employee/{employeeId}")
    public ApiResult deleteEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return deleteById(employeeId);
    }

    @PutMapping("/studentPWD")
    public ApiResult updatePwd(@RequestBody Employee employee) {
        employeeService.updatePwd(employee);
        return ApiResultHandler.buildApiResult(200,"密码更新成功",null);
    }

    @PutMapping("/employeePWD")
    public ApiResult updateEmployeePwd(@RequestBody Employee employee) {
        return updatePwd(employee);
    }
    @PutMapping("/student")
    public ApiResult update(@RequestBody Employee employee) {
        int res = employeeService.update(employee);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"更新成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"更新失败",res);
    }

    @PutMapping("/employee")
    public ApiResult updateEmployee(@RequestBody Employee employee) {
        return update(employee);
    }

    @PostMapping("/student")
    public ApiResult add(@RequestBody Employee employee) {
        int res = employeeService.add(employee);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"添加成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"添加失败",null);
        }
    }

    @PostMapping("/employee")
    public ApiResult addEmployee(@RequestBody Employee employee) {
        return add(employee);
    }
}
