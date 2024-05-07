import { Component, OnInit } from '@angular/core';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements OnInit {
  employee: Employee | undefined;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    const userId = 1;
    this.employeeService.getEmployeeByUserId(userId).subscribe((employee: any) => {
      this.employee = employee;
    });
  }
}
