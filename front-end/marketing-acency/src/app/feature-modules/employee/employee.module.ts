import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeProfileComponent } from './employee-profile/employee-profile.component';
import { EditEmployeeProfileComponent } from './edit-employee-profile/edit-employee-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdvertisementModule } from '../advertisement/advertisement.module';



@NgModule({
  declarations: [
    EmployeeProfileComponent,
    EditEmployeeProfileComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdvertisementModule
  ],
  exports: [
    EmployeeProfileComponent
  ]
})
export class EmployeeModule { }
