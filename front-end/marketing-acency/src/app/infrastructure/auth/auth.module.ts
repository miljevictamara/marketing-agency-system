import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { UserModule } from 'src/app/feature-modules/user/user.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';


@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    UserModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule
  ],
  exports: [
    LoginComponent
  ]
})
export class AuthModule { }
