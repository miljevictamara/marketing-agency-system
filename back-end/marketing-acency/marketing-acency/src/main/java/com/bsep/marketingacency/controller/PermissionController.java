package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.EmployeeService;
import com.bsep.marketingacency.service.PermissionService;
import com.bsep.marketingacency.service.RoleService;
import com.bsep.marketingacency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;


    @GetMapping("/roles/{roleName}/permissions")
    @PreAuthorize("hasAuthority('GET_PERMISSIONS_FOR_ROLE')")
    public Set<Permission> getPermissionsForRole(@PathVariable String roleName) {
        Role role = roleService.findByName(roleName);
        return role.getPermissions();
    }


    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('GET_ALL_PERMISSIONS')")
    public List<Permission> getPermissions() {
        return permissionService.getPermissions();
    }


    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('GET_ALL_ROLES')")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @PutMapping("/roles/{roleName}/permissions")
    @PreAuthorize("hasAuthority('UPDATE_ROLE_PERMISSION')")
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

    @GetMapping("/{mail}/{permissionName}")
    public boolean hasPermission(@PathVariable String permissionName,@PathVariable String mail) {
        User user = userService.findByMail(mail);

        if (user != null) {
            return user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .anyMatch(permission -> permission.getName().equals(permissionName));
        }
        return false;
    }


    @GetMapping("/check/{roleName}/{loggedUserRole}")
    public boolean getPermissions(@PathVariable String roleName, @PathVariable String loggedUserRole) {
        Role role = roleService.findByName(roleName);
        Role loggedInRole = roleService.findByName(loggedUserRole);

        Set<Permission> rolePermissions = role.getPermissions();
        Set<Permission> loggedInRolePermissions = loggedInRole.getPermissions();

        for (Permission permission : rolePermissions) {
            if (loggedInRolePermissions.contains(permission)) {
                return true;
            }
        }

        return false;
    }
}
