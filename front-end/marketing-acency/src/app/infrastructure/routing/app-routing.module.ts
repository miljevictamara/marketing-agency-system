import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/feature-modules/layout/home/home.component';
import { LoginComponent } from '../auth/login/login.component';
import { RegistrationComponent } from '../auth/registration/registration.component';
import { ActivationComponent } from '../auth/activation/activation.component';
import { ForbiddenComponent } from '../auth/forbidden/forbidden.component';
import { EmployeeProfileComponent } from 'src/app/feature-modules/employee/employee-profile/employee-profile.component';
import { RegistrationRequestComponent } from 'src/app/feature-modules/administrator/registration-request/registration-request.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'activation/:tokenId', component: ActivationComponent},
  { path: '403', component: ForbiddenComponent},
  { path: 'employee-profile', component: EmployeeProfileComponent},
  { path: 'registration-requests', component: RegistrationRequestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
