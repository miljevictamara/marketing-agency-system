import { Component, OnInit } from '@angular/core';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { PermissionService } from '../../permission-page/permission.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements OnInit {
  employee: Employee | undefined;
  editClicked: boolean = false;
  user: User | undefined;
  getById: boolean = false;
  getPendingRequests: boolean = false;
  getAcceptedRequests: boolean = false;
  updateEmployee: boolean = false;

  constructor(private employeeService: EmployeeService, private authService: AuthService, private permission: PermissionService) { }

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    if (userId !== undefined) {
      this.employeeService.getEmployeeByUserId(userId).subscribe((employee: any) => {
        this.employee = employee;
        console.log(employee);
      });
    } else {
      console.error('User ID is undefined.');
    }
    this.authService.user$.subscribe(user => {
      this.user = user;
      this.permission.hasPermission(this.user.mail, 'GET_EMPLOYEE_BYUSERID').subscribe(hasPermission => {
        this.getById = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_PENDING_ADVERTISMENTS').subscribe(hasPermission => {
        this.getPendingRequests = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_ACCEPTED_ADVERTISMENTS').subscribe(hasPermission => {
        this.getAcceptedRequests = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'UPDATE_EMPLOYEE').subscribe(hasPermission => {
        this.updateEmployee = hasPermission;
      });
    });
  }

  onEditClicked(employee: Employee) {
    this.employee = employee;
    this.editClicked = !this.editClicked;
  }
  
}
