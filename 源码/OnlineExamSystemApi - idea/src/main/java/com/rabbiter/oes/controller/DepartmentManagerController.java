package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.DepartmentManager;
import com.rabbiter.oes.serviceimpl.DepartmentManagerServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentManagerController {

    private DepartmentManagerServiceImpl departmentManagerService;
    @Autowired
    public DepartmentManagerController(DepartmentManagerServiceImpl departmentManagerService){
        this.departmentManagerService = departmentManagerService;
    }

    @GetMapping("/teachers/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size){
        Page<DepartmentManager> pageObj = new Page<>(page,size);
        IPage<DepartmentManager> result = departmentManagerService.findAll(pageObj);

        return ApiResultHandler.buildApiResult(200,"查询所有部门管理员",result);
    }

    @GetMapping("/departmentManagers/{page}/{size}")
    public ApiResult findAllDepartmentManagers(@PathVariable Integer page, @PathVariable Integer size){
        return findAll(page, size);
    }

    @GetMapping("/teacher/{teacherId}")
    public ApiResult findById(@PathVariable("teacherId") Integer teacherId){
        return ApiResultHandler.success(departmentManagerService.findById(teacherId));
    }

    @GetMapping("/departmentManager/{departmentManagerId}")
    public ApiResult findDepartmentManagerById(@PathVariable("departmentManagerId") Integer departmentManagerId){
        return findById(departmentManagerId);
    }

    @DeleteMapping("/teacher/{teacherId}")
    public ApiResult deleteById(@PathVariable("teacherId") Integer teacherId){
        return ApiResultHandler.success(departmentManagerService.deleteById(teacherId));
    }

    @DeleteMapping("/departmentManager/{departmentManagerId}")
    public ApiResult deleteDepartmentManagerById(@PathVariable("departmentManagerId") Integer departmentManagerId){
        return deleteById(departmentManagerId);
    }

    @PutMapping("/teacher")
    public ApiResult update(@RequestBody DepartmentManager departmentManager){
        return ApiResultHandler.success(departmentManagerService.update(departmentManager));
    }

    @PutMapping("/departmentManager")
    public ApiResult updateDepartmentManager(@RequestBody DepartmentManager departmentManager){
        return update(departmentManager);
    }

    @PostMapping("/teacher")
    public ApiResult add(@RequestBody DepartmentManager departmentManager){
        return ApiResultHandler.success(departmentManagerService.add(departmentManager));
    }

    @PostMapping("/departmentManager")
    public ApiResult addDepartmentManager(@RequestBody DepartmentManager departmentManager){
        return add(departmentManager);
    }
}
