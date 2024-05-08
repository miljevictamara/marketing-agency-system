import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdvertisementsComponent } from './advertisements/advertisements.component';
import { RequestsComponent } from './requests/requests.component';



@NgModule({
  declarations: [
    AdvertisementsComponent,
    RequestsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AdvertisementsComponent,
    RequestsComponent
  ]
})
export class AdvertisementModule { }
