import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdvertisementsComponent } from './advertisements/advertisements.component';
import { RequestsComponent } from './requests/requests.component';
import { AdvertisementsForUserComponent } from './advertisements-for-user/advertisements-for-user.component';
import { AdvertisementFormComponent } from './advertisement-form/advertisement-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AdvertisementsComponent,
    RequestsComponent,
    AdvertisementsForUserComponent,
    AdvertisementFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    AdvertisementsComponent,
    RequestsComponent,
    AdvertisementsForUserComponent,
    AdvertisementFormComponent
  ]
})
export class AdvertisementModule { }
