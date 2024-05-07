import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Employee } from '../model/employee.model';

@Component({
  selector: 'app-edit-employee-profile',
  templateUrl: './edit-employee-profile.component.html',
  styleUrls: ['./edit-employee-profile.component.css']
})
export class EditEmployeeProfileComponent implements OnChanges{
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
    console.log(this.employeeForm.value);
  }
}
