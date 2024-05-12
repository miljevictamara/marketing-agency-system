import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsComponent } from './clients/clients.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ClientProfileComponent } from './client-profile/client-profile.component';



@NgModule({
  declarations: [
    ClientsComponent,
    ClientProfileComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  exports: [
    ClientsComponent,
    ClientProfileComponent
  ]
})
export class ClientModule { }
