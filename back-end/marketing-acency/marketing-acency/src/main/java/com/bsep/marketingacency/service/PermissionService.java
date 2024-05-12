package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.PermissionRepository;
import com.bsep.marketingacency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;


    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
    }

    public List<Permission> getPermissions(){
        List<Permission> permissions = new ArrayList<>();
        for(Permission p : permissionRepository.findAll()){
            permissions.add(p);

        }
        return permissions;
    }

}
