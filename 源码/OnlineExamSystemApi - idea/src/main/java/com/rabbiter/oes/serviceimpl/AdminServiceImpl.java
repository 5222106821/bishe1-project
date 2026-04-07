package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.DepartmentManager;
import com.rabbiter.oes.mapper.AdminMapper;
import com.rabbiter.oes.mapper.DepartmentManagerMapper;
import com.rabbiter.oes.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DepartmentManagerMapper departmentManagerMapper;

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Admin findById(Integer adminId) {
        return adminMapper.findById(adminId);
    }

    @Override
    public int deleteById(int adminId) {
        return adminMapper.deleteById(adminId);
    }

    @Override
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public int add(Admin admin) {
        return 0;
    }

    @Override
    public Object resetPsw(Integer adminId, String newPsw, String oldPsw) {
        Admin admin = findById(adminId);
        if(admin != null && admin.getPwd().equals(oldPsw)) {
            admin.setPwd(String.valueOf(newPsw));
            update(admin);
            return true;

        }else if(admin == null){
            DepartmentManager departmentManager = departmentManagerMapper.findById(adminId);
            if(departmentManager != null && departmentManager.getPwd().equals(oldPsw)) {
                departmentManager.setPwd(String.valueOf(newPsw));
                departmentManagerMapper.update(departmentManager);
                return true;

            }
        }
        return "原密码错误";
    }


}
