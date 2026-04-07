package com.rabbiter.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DepartmentManager;

import java.util.List;

public interface DepartmentManagerService {

    IPage<DepartmentManager> findAll(Page<DepartmentManager> page);

    public List<DepartmentManager> findAll();

    public DepartmentManager findById(Integer teacherId);

    public int deleteById(Integer teacherId);

    public int update(DepartmentManager departmentManager);

    public int add(DepartmentManager departmentManager);
}
