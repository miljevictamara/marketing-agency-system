import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Permission } from 'src/app/infrastructure/auth/model/permission.model';
import { Role } from 'src/app/infrastructure/auth/model/role.model';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  constructor(private http: HttpClient) { }

  getPermissionsForRole(roleName: String): Observable<Permission[]> {
    return this.http.get<Permission[]>(`https://localhost:8443/permissions/roles/${roleName}/permissions`);
  }

  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`https://localhost:8443/permissions/roles`);
  }

  getAllPermissions(): Observable<Permission[]> {
    return this.http.get<Permission[]>(`https://localhost:8443/permissions/permissions`);
  }

  updateRolePermissions(roleName: string, permissions: Permission[]): Observable<any> {
    return this.http.put<any>(`https://localhost:8443/permissions/roles/${roleName}/permissions`, permissions);
  }

  hasPermission(mail: string,permissionName: string): Observable<boolean> {
    return this.http.get<boolean>(`https://localhost:8443/permissions/${mail}/${permissionName}`);
   
  }

}
