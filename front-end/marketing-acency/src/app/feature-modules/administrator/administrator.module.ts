import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditAdministratorProfileComponent } from './edit-administrator-profile/edit-administrator-profile.component';
import { AdministratorProfileComponent } from './administrator-profile/administrator-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EmployeeModule } from '../employee/employee.module';
import { ClientModule } from '../client/client.module';
import { AdministratorFormComponent } from './administrator-form/administrator-form.component';



@NgModule({
  declarations: [
    AdministratorProfileComponent,
    EditAdministratorProfileComponent,
    AdministratorFormComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    EmployeeModule,
    ClientModule
  ],
  exports: [
    AdministratorProfileComponent,
    AdministratorFormComponent
  ]
})
export class AdministratorModule { }
