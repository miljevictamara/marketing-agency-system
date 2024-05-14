import { Component, OnInit } from '@angular/core';
import { Administrator } from '../model/administrator.model';
import { AdministratorService } from '../administrator.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';
import { PermissionService } from '../../permission-page/permission.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Component({
  selector: 'app-administrator-profile',
  templateUrl: './administrator-profile.component.html',
  styleUrls: ['./administrator-profile.component.css']
})
export class AdministratorProfileComponent implements OnInit {
  administrator: Administrator | undefined;
  editClicked: boolean = false;
  hasEmployeePermission: boolean = false;
  getByUserId: boolean = false;
  getAllCLients: boolean = false;
  createEmployee: boolean = false;
  managePermissions: boolean = false;
  createAdmin: boolean = false;
  updateAdmin: boolean = false;

  user: User | undefined;
  constructor(private router: Router, private administratorService: AdministratorService, private authService: AuthService, private permission: PermissionService) { }

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    this.authService.user$.subscribe(user => {
      this.user = user;
      this.permission.hasPermission(this.user.mail, 'GET_ALL_EMPLOYEES').subscribe(hasPermission => {
        this.hasEmployeePermission = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_BYUSERID').subscribe(hasPermission => {
        this.getByUserId = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_ALL_CLIENTS').subscribe(hasPermission => {
        this.getAllCLients = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'CREATE_EMPLOYEE').subscribe(hasPermission => {
        this.createEmployee = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_ALL_PERMISSIONS').subscribe(hasPermission => {
        this.managePermissions = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'CREATE_ADMIN').subscribe(hasPermission => {
        this.createAdmin = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'UPDATE_ADMIN').subscribe(hasPermission => {
        this.updateAdmin = hasPermission;
      });
    });
    if (userId !== undefined) {
      this.administratorService.getAdministratorByUserId(userId).subscribe((administrator: any) => {
        this.administrator = administrator;
        console.log(administrator);
      });
    } else {
      console.error('User ID is undefined.');
    }
  }



  onEditClicked(administrator: Administrator) {
    this.administrator = administrator;
    this.editClicked = true;
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
