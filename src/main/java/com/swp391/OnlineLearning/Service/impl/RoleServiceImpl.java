package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.Repository.RoleRepository;
import com.swp391.OnlineLearning.Service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public List<UserRole> findAll() {
        return this.roleRepository.findAll();
    }
}
