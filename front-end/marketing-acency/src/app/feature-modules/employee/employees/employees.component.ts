import { Component } from '@angular/core';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent {
  employees: Employee[] | undefined;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    this.employeeService.getEmployees()
      .subscribe(employee => this.employees = employee);
  }
}
