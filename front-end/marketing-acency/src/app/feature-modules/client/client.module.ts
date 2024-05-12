import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsComponent } from './clients/clients.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ClientProfileComponent } from './client-profile/client-profile.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { AdvertisementModule } from '../advertisement/advertisement.module';



@NgModule({
  declarations: [
    ClientsComponent,
    ClientProfileComponent,
    EditClientProfileComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdvertisementModule
  ],
  exports: [
    ClientsComponent,
    ClientProfileComponent
  ]
})
export class ClientModule { }
