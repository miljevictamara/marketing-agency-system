package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.PermissionController;
import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    private Logger logger =  LoggerFactory.getLogger(RoleService.class);

    public Role findOne(Long id) {
        return roleRepository.getById(id);
    }

//    public Role findByName(String name) {
//        return this.roleRepository.findByName(name);
//
//    }

    public Role findByName(String name) {
        Role role = this.roleRepository.findByName(name);
        if (role == null) {
            logger.warn("Role {} not found.", name);
        }
        return role;
    }


    public List<Role> getRoles(){
        List<Role> roles = new ArrayList<>();
        for(Role r : roleRepository.findAll()){
            roles.add(r);

        }
        return roles;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
