import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserModule } from 'src/app/feature-modules/user/user.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegistrationComponent } from './registration/registration.component';
import { ActivationComponent } from './activation/activation.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordlessLoginComponent } from './passwordless-login/passwordless-login.component';
import { CheckYourEmailComponent } from './check-your-email/check-your-email.component';
import { ForbiddenPasswordlessLoginComponent } from './forbidden-passwordless-login/forbidden-passwordless-login.component';
import { SuccessfulPasswordlessLoginComponent } from './successful-passwordless-login/successful-passwordless-login.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    ActivationComponent,
    ForbiddenComponent,
    PasswordlessLoginComponent,
    CheckYourEmailComponent,
    ForbiddenPasswordlessLoginComponent,
    SuccessfulPasswordlessLoginComponent
  ],
  imports: [
    CommonModule,
    UserModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
  ],
  exports: [
    LoginComponent,
    RegistrationComponent,
    ActivationComponent,
    ForbiddenComponent,
    PasswordlessLoginComponent,
    CheckYourEmailComponent,
    ForbiddenPasswordlessLoginComponent
  ]
})
export class AuthModule { }
