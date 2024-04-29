package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findOne(Long id) {
        return roleRepository.getById(id);
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name);

    }
}
