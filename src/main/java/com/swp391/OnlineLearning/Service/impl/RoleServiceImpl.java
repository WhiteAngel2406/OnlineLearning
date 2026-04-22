package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.model.UserRole;
import com.swp391.OnlineLearning.repository.RoleRepository;
import com.swp391.OnlineLearning.service.RoleService;
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
