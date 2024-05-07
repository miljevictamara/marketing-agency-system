import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeProfileComponent } from './employee-profile/employee-profile.component';



@NgModule({
  declarations: [
    EmployeeProfileComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    EmployeeProfileComponent
  ]
})
export class EmployeeModule { }
