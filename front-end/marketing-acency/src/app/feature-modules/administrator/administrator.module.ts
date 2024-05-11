import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditAdministratorProfileComponent } from './edit-administrator-profile/edit-administrator-profile.component';
import { AdministratorProfileComponent } from './administrator-profile/administrator-profile.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AdministratorProfileComponent,
    EditAdministratorProfileComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  exports: [
    AdministratorProfileComponent
  ]
})
export class AdministratorModule { }
