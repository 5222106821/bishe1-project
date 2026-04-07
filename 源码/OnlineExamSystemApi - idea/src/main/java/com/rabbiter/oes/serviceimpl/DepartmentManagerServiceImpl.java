package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DepartmentManager;
import com.rabbiter.oes.mapper.DepartmentManagerMapper;
import com.rabbiter.oes.service.DepartmentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentManagerServiceImpl implements DepartmentManagerService {
    @Autowired
    private DepartmentManagerMapper departmentManagerMapper;

    @Override
    public IPage<DepartmentManager> findAll(Page<DepartmentManager> page) {
        return departmentManagerMapper.findAll(page);
    }

    @Override
    public List<DepartmentManager> findAll() {
        Page<DepartmentManager> page = new Page<>(0,9999);
        return departmentManagerMapper.findAll(page).getRecords();
    }

    @Override
    public DepartmentManager findById(Integer teacherId) {
        return departmentManagerMapper.findById(teacherId);
    }

    @Override
    public int deleteById(Integer teacherId) {
        return departmentManagerMapper.deleteById(teacherId);
    }

    @Override
    public int update(DepartmentManager departmentManager) {
        return departmentManagerMapper.update(departmentManager);
    }

    @Override
    public int add(DepartmentManager departmentManager) {
        departmentManager.setRole("1");
        return departmentManagerMapper.add(departmentManager);
    }
}
