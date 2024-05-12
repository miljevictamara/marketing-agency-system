import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/feature-modules/layout/home/home.component';
import { LoginComponent } from '../auth/login/login.component';
import { RegistrationComponent } from '../auth/registration/registration.component';
import { ActivationComponent } from '../auth/activation/activation.component';
import { ForbiddenComponent } from '../auth/forbidden/forbidden.component';
import { PasswordlessLoginComponent } from '../auth/passwordless-login/passwordless-login.component';
import { CheckYourEmailComponent } from '../auth/check-your-email/check-your-email.component';
import { ForbiddenPasswordlessLoginComponent } from '../auth/forbidden-passwordless-login/forbidden-passwordless-login.component';
import { SuccessfulPasswordlessLoginComponent } from '../auth/successful-passwordless-login/successful-passwordless-login.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'activation/:tokenId', component: ActivationComponent},
  { path: '403', component: ForbiddenComponent},
  { path: 'passwordless-login', component: PasswordlessLoginComponent},
  { path: 'check-your-email', component: CheckYourEmailComponent},
  { path: 'forbidden-passwordless-login', component: ForbiddenPasswordlessLoginComponent},
  { path: 'passwordless-login-link/:tokenId', component: SuccessfulPasswordlessLoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
