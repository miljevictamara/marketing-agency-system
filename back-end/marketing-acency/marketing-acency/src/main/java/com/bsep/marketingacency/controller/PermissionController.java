package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.EmployeeService;
import com.bsep.marketingacency.service.PermissionService;
import com.bsep.marketingacency.service.RoleService;
import com.bsep.marketingacency.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private Logger logger =  LoggerFactory.getLogger(PermissionController.class);

//    @GetMapping("/roles/{roleName}/permissions")
//    @PreAuthorize("hasAuthority('GET_PERMISSIONS_FOR_ROLE')")
//    public Set<Permission> getPermissionsForRole(@PathVariable String roleName) {
//        Role role = roleService.findByName(roleName);
//        return role.getPermissions();
//    }

    @GetMapping("/roles/{roleName}/permissions")
    @PreAuthorize("hasAuthority('GET_PERMISSIONS_FOR_ROLE')")
    public Set<Permission> getPermissionsForRole(@PathVariable String roleName) {
        try {
            Role role = roleService.findByName(roleName);
            return role.getPermissions();
        } catch (Exception e) {
            logger.error("Error retrieving permissions for role {}.", roleName);
            throw e;
        }
    }



//    @GetMapping("/permissions")
//    @PreAuthorize("hasAuthority('GET_ALL_PERMISSIONS')")
//    public List<Permission> getPermissions() {
//        return permissionService.getPermissions();
//    }

    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('GET_ALL_PERMISSIONS')")
    public List<Permission> getPermissions() {
        try {
            return permissionService.getPermissions();
        } catch (Exception e) {
            logger.error("Error fetching all permissions.");
            throw e;
        }
    }
//    @GetMapping("/roles")
//    @PreAuthorize("hasAuthority('GET_ALL_ROLES')")
//    public List<Role> getRoles() {
//        return roleService.getRoles();
//    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('GET_ALL_ROLES')")
    public List<Role> getRoles() {
        try {
            return roleService.getRoles();
        } catch (Exception e) {
            logger.error("Error fetching all roles.");
            throw e;
        }
    }


//    @PutMapping("/roles/{roleName}/permissions")
//    @PreAuthorize("hasAuthority('UPDATE_ROLE_PERMISSION')")
//    public ResponseEntity<?> updateRolePermissions(@PathVariable String roleName, @RequestBody Set<Permission> updatedPermissions) {
//        Role role = roleService.findByName(roleName);
//        if (role == null) {
//            return ResponseEntity.notFound().build();
//        }
//        role.setPermissions(updatedPermissions);
//        roleService.save(role);
//
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/roles/{roleName}/permissions")
    @PreAuthorize("hasAuthority('UPDATE_ROLE_PERMISSION')")
    public ResponseEntity<?> updateRolePermissions(@PathVariable String roleName, @RequestBody Set<Permission> updatedPermissions) {
        Role role = roleService.findByName(roleName);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            role.setPermissions(updatedPermissions);
            roleService.save(role);
            String updatedPermissionsInfo = "Updated permissions for role '" + roleName + ".";
            logger.info(updatedPermissionsInfo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating permissions for role '{}'.", roleName);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating permissions for role.");
        }
    }


//    @GetMapping("/{mail}/{permissionName}")
//    public boolean hasPermission(@PathVariable String permissionName,@PathVariable String mail) {
//        User user = userService.findByMail(mail);
//
//        if (user != null) {
//            return user.getRoles().stream()
//                    .flatMap(role -> role.getPermissions().stream())
//                    .anyMatch(permission -> permission.getName().equals(permissionName));
//        }
//        return false;
//    }

    @GetMapping("/{mail}/{permissionName}")
    public Boolean hasPermission(@PathVariable String permissionName, @PathVariable String mail) {
        try {
            User user = userService.findByMail(mail);
            if (user != null) {
                return user.getRoles().stream()
                  .flatMap(role -> role.getPermissions().stream())
                  .anyMatch(permission -> permission.getName().equals(permissionName));
          }
            return false;

        } catch (Exception e) {
            logger.error("Error checking permission for {}.", mail);
            throw e;
        }
    }



//    @GetMapping("/check/{roleName}/{loggedUserRole}")
//    public boolean getPermissions(@PathVariable String roleName, @PathVariable String loggedUserRole) {
//        Role role = roleService.findByName(roleName);
//        Role loggedInRole = roleService.findByName(loggedUserRole);
//
//        Set<Permission> rolePermissions = role.getPermissions();
//        Set<Permission> loggedInRolePermissions = loggedInRole.getPermissions();
//
//        for (Permission permission : rolePermissions) {
//            if (loggedInRolePermissions.contains(permission)) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    @GetMapping("/check/{roleName}/{loggedUserRole}")
    public boolean getPermissions(@PathVariable String roleName, @PathVariable String loggedUserRole) {
        try {
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
        } catch (Exception e) {
            logger.error("Error checking permissions for roles '{}', '{}'.", roleName, loggedUserRole);
            throw e;
        }
    }

}
