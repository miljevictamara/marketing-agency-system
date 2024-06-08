import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsComponent } from './clients/clients.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ClientProfileComponent } from './client-profile/client-profile.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { AdvertisementModule } from '../advertisement/advertisement.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';



@NgModule({
  declarations: [
    ClientsComponent,
    ClientProfileComponent,
    EditClientProfileComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdvertisementModule,
    MatFormFieldModule,
    MatInputModule
  ],
  exports: [
    ClientsComponent,
    ClientProfileComponent
  ]
})
export class ClientModule { }
