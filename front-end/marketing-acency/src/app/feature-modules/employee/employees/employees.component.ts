import { Component } from '@angular/core';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';
import { UserService } from '../../user/user.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent {
  employees: Employee[] | undefined;

  constructor(private employeeService: EmployeeService, private userService: UserService) { }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    this.employeeService.getEmployees()
      .subscribe(employee => this.employees = employee);
  }

  blockEmployee(userId: number): void {
    if (userId !== undefined) {
      this.userService.updateIsBlocked(userId).subscribe(
        response => {
          console.log('User blocked successfully', response);
          alert("Employee blocked!")
        },
        error => {
          console.error('Error blocking user', error);
        }
      );
    }
  }
}
