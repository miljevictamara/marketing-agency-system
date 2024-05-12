package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.service.EmployeeService;
import com.bsep.marketingacency.service.PermissionService;
import com.bsep.marketingacency.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('GET_PERMISSIONS_FOR_ROLE_ADMIN_PERMISSION')")

    @GetMapping("/roles/{roleName}/permissions")
    public Set<Permission> getPermissionsForRole(@PathVariable String roleName) {
        Role role = roleService.findByName(roleName);
        return role.getPermissions();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN_PERMISSION')")

    @GetMapping("/permissions")
    public List<Permission> getPermissions() {
        return permissionService.getPermissions();
    }

    @PreAuthorize("hasAuthority('GET_PERMISSIONS_ADMIN_PERMISSION')")

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }
    @PreAuthorize("hasAuthority('UPDATE_ADMIN_PERMISSION')")

    @PutMapping("/roles/{roleName}/permissions")
    public ResponseEntity<?> updateRolePermissions(@PathVariable String roleName, @RequestBody Set<Permission> updatedPermissions) {
        // Pronalazak uloge iz baze
        Role role = roleService.findByName(roleName);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        role.setPermissions(updatedPermissions);
        roleService.save(role);

        return ResponseEntity.ok().build();
    }
}
