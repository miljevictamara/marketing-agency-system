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
import { EmployeeProfileComponent } from 'src/app/feature-modules/employee/employee-profile/employee-profile.component';
import { AdministratorProfileComponent } from 'src/app/feature-modules/administrator/administrator-profile/administrator-profile.component';
import { EmployeeFormComponent } from 'src/app/feature-modules/employee/employee-form/employee-form.component';
import { AdministratorFormComponent } from 'src/app/feature-modules/administrator/administrator-form/administrator-form.component';
import { AuthGuard } from '../authorization/auth.guard';
import { PermissionPageComponent } from 'src/app/feature-modules/permission-page/permission-page.component';
import { ClientProfileComponent } from 'src/app/feature-modules/client/client-profile/client-profile.component';
import { AdvertisementFormComponent } from 'src/app/feature-modules/advertisement/advertisement-form/advertisement-form.component';
import { RegistrationRequestComponent } from 'src/app/feature-modules/administrator/registration-request/registration-request.component';
import { QrcodeComponent } from '../auth/qrcode/qrcode.component';
import { TwoFAVerificationComponent } from '../auth/two-fa-verification/two-fa-verification.component';
import { LogMessagesComponent } from '../monitoring/log-messages/log-messages.component';
import { VpnComponent } from 'src/app/feature-modules/user/vpn/vpn.component';
import { ResetPasswordRequestComponent } from '../auth/reset-password-request/reset-password-request.component';
import { ResetPasswordComponent } from '../auth/reset-password/reset-password.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'activation/:tokenId/:hmac', component: ActivationComponent},
  { path: '403', component: ForbiddenComponent},
  { path: 'passwordless-login', component: PasswordlessLoginComponent},
  { path: 'check-your-email', component: CheckYourEmailComponent},
  { path: 'forbidden-passwordless-login', component: ForbiddenPasswordlessLoginComponent},
  { path: 'passwordless-login-link/:tokenId', component: SuccessfulPasswordlessLoginComponent},
  { path: 'administrator-profile', component: AdministratorProfileComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}},
  { path: 'employee-form', component: EmployeeFormComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}},
  { path: 'administrator-form', component: AdministratorFormComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']} },
  { path: 'employee-profile', component: EmployeeProfileComponent, canActivate:[AuthGuard], data:{role:['ROLE_EMPLOYEE']}},
  { path: 'permission-page', component: PermissionPageComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}},
  { path: 'client-profile', component: ClientProfileComponent, canActivate:[AuthGuard], data:{role:['ROLE_CLIENT']}},
  { path: 'advertisement-form', component: AdvertisementFormComponent, canActivate:[AuthGuard], data:{role:['ROLE_CLIENT']}},
  { path: 'registration-requests', component: RegistrationRequestComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}},
  { path: 'qrcode', component: QrcodeComponent},
  { path: 'verify/:mail', component: TwoFAVerificationComponent },
  { path: 'log', component: LogMessagesComponent },
  { path: 'vpn', component: VpnComponent },
  { path: 'reset-password-request', component: ResetPasswordRequestComponent },
  { path: 'reset-password', component: ResetPasswordComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
