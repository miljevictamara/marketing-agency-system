import { Component, OnInit } from '@angular/core';
import { Permission } from 'src/app/infrastructure/auth/model/permission.model';
import { PermissionService } from './permission.service';
import { Role } from 'src/app/infrastructure/auth/model/role.model';

@Component({
  selector: 'app-permission-page',
  templateUrl: './permission-page.component.html',
  styleUrls: ['./permission-page.component.css']
})
export class PermissionPageComponent implements OnInit {
  selectedRole: Role| undefined ;  
  roles: Role[] = [];
  permissions: Permission[] = [];
  selectedPermissions: { [key: string]: boolean } = {};

  constructor(private permissionService: PermissionService) { }

  ngOnInit(): void {
    this.loadRoles();
    this.loadPermissions();
  }

  loadRoles(): void {
    this.permissionService.getAllRoles().subscribe((roles: Role[]) => {
      this.roles = roles; 
    });
  }

  loadPermissions(): void {
    this.permissionService.getAllPermissions().subscribe((permissions: Permission[]) => {
      this.permissions = permissions; 
    });
  }

  onRoleSelectionChange() {
    if (this.selectedRole) {
        this.permissionService.getPermissionsForRole(this.selectedRole.name).subscribe((permissions: Permission[]) => {
            this.selectedPermissions = {};
            permissions.forEach(permission => {
                this.selectedPermissions[permission.name] = true;
            });
        });
    } else {
        this.selectedPermissions = {};
    }
}
  

  isChecked(permission: Permission): boolean {
    return this.selectedPermissions[permission.name] || false;
  }

  onPermissionChange(permission: Permission) {
    if (this.selectedRole) {
      if (this.selectedPermissions[permission.name]) {
        const index = this.selectedRole.permissions.findIndex(p => p.name === permission.name);
        if (index !== -1) {
          this.selectedRole.permissions.splice(index, 1);
        }
      } else {
        this.selectedRole.permissions.push(permission);
      }
    }
  }

  onSavePermissions() {
    if (this.selectedRole) {
      this.permissionService.updateRolePermissions(this.selectedRole.name, this.selectedRole.permissions)
        .subscribe(() => {
          alert("Successfully updated")
          console.log('Permissions updated successfully');
        }, error => {
          console.error('Error updating permissions:', error);
        });
    }
  }
  
}
