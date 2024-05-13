import { Component, OnInit } from '@angular/core';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';

@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements OnInit {
  employee: Employee | undefined;
  editClicked: boolean = false;

  constructor(private employeeService: EmployeeService, private authService: AuthService) { }

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
  }

  onEditClicked(employee: Employee) {
    this.employee = employee;
    this.editClicked = !this.editClicked; // Toggle the value
  }
  
}
