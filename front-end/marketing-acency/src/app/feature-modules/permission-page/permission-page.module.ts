import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PermissionPageComponent } from './permission-page.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    PermissionPageComponent,
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    PermissionPageComponent
  ]
})
export class PermissionPageModule { }
