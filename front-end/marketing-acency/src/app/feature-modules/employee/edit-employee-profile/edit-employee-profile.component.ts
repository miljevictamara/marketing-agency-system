import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Employee } from '../model/employee.model';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-edit-employee-profile',
  templateUrl: './edit-employee-profile.component.html',
  styleUrls: ['./edit-employee-profile.component.css']
})
export class EditEmployeeProfileComponent implements OnChanges{

  constructor(private employeeService: EmployeeService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.employee) {
      this.employeeForm.patchValue(this.employee);
    }
  }
  

  @Input() employee: Employee | undefined;

  employeeForm = new FormGroup ({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.required)
  })

  editEmployeeProfile(): void {
    if (this.employeeForm.valid && this.employee) {
      const updatedEmployee: Employee = { ...this.employee, ...this.employeeForm.value };
      this.employeeService.updateEmployee(updatedEmployee).subscribe(
        (updated: Employee) => {
          console.log('Employee updated successfully:', updated);
          // Optionally, perform any additional actions after the update
        },
        (error) => {
          console.error('Failed to update employee:', error);
          // Optionally, handle the error
        }
      );
    }
  }
}
