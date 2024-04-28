import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { ROUTES, Router, RouterModule } from '@angular/router';
import { AppRoutingModule } from 'src/app/infrastructure/routing/app-routing.module';
import { PackagesComponent } from './packages/packages.component';

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    PackagesComponent
  ],
  imports: [
    CommonModule,
    MatMenuModule,
    MatToolbarModule,
    MatButtonModule,
    RouterModule.forChild([]),
  ],
  exports: [
    HomeComponent,
    FooterComponent,
    NavbarComponent
  ]
})
export class LayoutModule { }
