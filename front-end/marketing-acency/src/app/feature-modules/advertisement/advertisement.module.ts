import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdvertisementsComponent } from './advertisements/advertisements.component';
import { RequestsComponent } from './requests/requests.component';
import { AdvertisementsForUserComponent } from './advertisements-for-user/advertisements-for-user.component';



@NgModule({
  declarations: [
    AdvertisementsComponent,
    RequestsComponent,
    AdvertisementsForUserComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AdvertisementsComponent,
    RequestsComponent,
    AdvertisementsForUserComponent
  ]
})
export class AdvertisementModule { }
