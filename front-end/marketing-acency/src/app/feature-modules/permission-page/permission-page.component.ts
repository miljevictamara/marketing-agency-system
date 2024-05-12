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
            // Resetujemo mapu selektovanih permisija
            this.selectedPermissions = {};
            // Postavljamo sve permisije za trenutno izabranu ulogu
            permissions.forEach(permission => {
                this.selectedPermissions[permission.name] = true;
            });
        });
    } else {
        // Ako nije izabrana uloga, resetujemo selektovane permisije
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
}
