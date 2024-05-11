import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeProfileComponent } from './employee-profile/employee-profile.component';
import { EditEmployeeProfileComponent } from './edit-employee-profile/edit-employee-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdvertisementModule } from '../advertisement/advertisement.module';
import { EmployeesComponent } from './employees/employees.component';



@NgModule({
  declarations: [
    EmployeeProfileComponent,
    EditEmployeeProfileComponent,
    EmployeesComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdvertisementModule
  ],
  exports: [
    EmployeeProfileComponent,
    EmployeesComponent
  ]
})
export class EmployeeModule { }
